package com.hertzai.hevolve.managers;

import android.content.Context;
import android.content.ContextWrapper;



import com.hertzai.hevolve.R;
import com.hertzai.hevolve.api.AskMeChatApi;
import com.hertzai.hevolve.api.RevisionChatApi;
import com.hertzai.hevolve.constants.hevEnv;
import com.hertzai.hevolve.managers.helper.ConnectivityInterceptor;
import com.hertzai.hevolve.managers.retrofit.SignLoginApi;

import com.hertzai.hevolve.models.appModel.HevolveApp;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitManager extends ContextWrapper {

    private static RetrofitManager mInstance;
    private Retrofit defaultRetrofit;
    private Retrofit userRetrofit;
    private Retrofit speakBookRetrofit;
    private Retrofit fileUploadRetrofit;


    private RetrofitManager(Context base) {
        super(base);
    }

    public static synchronized RetrofitManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new RetrofitManager(context.getApplicationContext());
        }
        return mInstance;
    }

    public void logout() {
        userRetrofit = null;
    }

    private Retrofit getDefaultRetrofit() {
        if (defaultRetrofit == null) {
            createDefaultRetrofit();
        }
        return defaultRetrofit;
    }


    private Retrofit getUserRetrofit() {
        if (userRetrofit == null) {
            createUserRetrofit();
        }
        return userRetrofit;
    }

    private Retrofit getSpeakToBookRetrofit() {
        if (speakBookRetrofit == null) {
            speakToBookRetrofit();
        }
        return speakBookRetrofit;
    }


    private void createDefaultRetrofit() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new ConnectivityInterceptor(getApplicationContext()));
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();

                Request request = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                        .build();
                return chain.proceed(request);
            }
        });


        if (!hevEnv.PROD_MODE.equals(1)) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logInterceptor);
        }


        httpClient.callTimeout(40, TimeUnit.SECONDS);
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();

        defaultRetrofit = new Retrofit.Builder().baseUrl(getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
    }

    private void createUserRetrofit() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new ConnectivityInterceptor(getApplicationContext()));
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();

            return chain.proceed(request);
        });

        if (!hevEnv.PROD_MODE.equals(1)) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logInterceptor);
        }


        httpClient.callTimeout(40, TimeUnit.SECONDS);
        httpClient.readTimeout(40, TimeUnit.SECONDS);
        OkHttpClient client = httpClient.build();
        userRetrofit = new Retrofit.Builder().baseUrl("http://assess.mcgroce.com")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

    }


    private void speakToBookRetrofit()
    {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder().addInterceptor(new ConnectivityInterceptor(getApplicationContext()));
        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Accept", "application/json")
                    .build();

            return chain.proceed(request);
        });

        if (!hevEnv.PROD_MODE.equals(1)) {
            HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
            logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(logInterceptor);
        }


        OkHttpClient client = httpClient.build();
        speakBookRetrofit = new Retrofit.Builder().baseUrl("http://cfb5cd75ea48.ngrok.io")
                       .client(client)
                      .addConverterFactory(GsonConverterFactory.create())
                      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();

    }


    public class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0)
                        return "success";
                    return delegate.convert(body);
                }
            };
        }
    }


    public void clearAll() {
        mInstance = null;
        defaultRetrofit = null;
        userRetrofit = null;
        fileUploadRetrofit = null;

    }

    public SignLoginApi getSignLoginApi() {
        return getDefaultRetrofit().create(SignLoginApi.class);
    }

    public RevisionChatApi getAccessToken_Revision()
    {return getUserRetrofit().create(RevisionChatApi.class);}

    public AskMeChatApi getBook()
    {
        return getDefaultRetrofit().create(AskMeChatApi.class);
    }
    public RevisionChatApi getRevision()
    {
        return getUserRetrofit().create(RevisionChatApi.class);
    }

}