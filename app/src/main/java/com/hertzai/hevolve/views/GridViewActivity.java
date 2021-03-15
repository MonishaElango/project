package com.hertzai.hevolve.views;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.hertzai.hevolve.R;
import com.hertzai.hevolve.adapter.MainAdapterGridView;
import com.hertzai.hevolve.api.AskMeChatApi;
import com.hertzai.hevolve.gson.TokenResponsePredict;
import com.hertzai.hevolve.models.appModel.MainModel;
import com.hertzai.hevolve.restService.ServiceGenerator;
import com.squareup.okhttp.Credentials;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class GridViewActivity extends AppCompatActivity {

    private CardView askmeCard, assessCard, devEngCard,revCard,speakCard;

    private final String clientId = "your-client-id";
    private final String clientSecret = "your-client-secret";

    private RecyclerView recylerViewImg;
    ArrayList<MainModel> mainModels;
    MainAdapterGridView mainImageAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_grid_view);

        recylerViewImg = findViewById(R.id.main_recyclerView);
        Integer[] teacherImg = {R.drawable.santa_bg,R.drawable.newton_bg,R.drawable.panda_bg,R.drawable.abdul_kalam_bg,R.drawable.chemistry_bg,R.drawable.marie_curie_bg,R.drawable.shiva_bg};
        String[] title  = {"One","Two","Three","Four","Five","Six","Seven"};
        mainModels = new ArrayList<>();
        mainModels.add(new MainModel(R.drawable.santa_bg, "One"));
        mainModels.add(new MainModel(R.drawable.newton_bg, "Two"));
        mainModels.add(new MainModel(R.drawable.panda_bg, "Three"));
        mainModels.add(new MainModel(R.drawable.abdul_kalam_bg, "Four"));
        mainModels.add(new MainModel(R.drawable.chemistry_bg, "Five"));
        mainModels.add(new MainModel(R.drawable.marie_curie_bg, "Six"));
        mainModels.add(new MainModel(R.drawable.shiva_bg, "Seven"));


         LinearLayoutManager layoutManager = new LinearLayoutManager(GridViewActivity.this,LinearLayoutManager.HORIZONTAL,false);
        recylerViewImg.setLayoutManager(layoutManager);
        recylerViewImg.setItemAnimator(new DefaultItemAnimator());
         mainImageAdapter = new MainAdapterGridView(mainModels,GridViewActivity.this, new MainAdapterGridView.MainRecyclerViewClickListener() {
             @Override
             public void onClick(int position) {
                 switch (position) {
                     case 1: {
                            showActivity();
                     }
                     case 2: {
                           showActivity();
                     }
                     case 3: {
                         showActivity();
                     }
                     case 4: {
                         showActivity();
                     }
                     case 5: {
                         showActivity();
                     }
                     case 6: {
                        showActivity();
                     }
                     case 7: {
                         showActivity();
                     }
                 }
             }
         });
         recylerViewImg.setAdapter(mainImageAdapter);


        askmeCard = (CardView) findViewById(R.id.teachCard);
        assessCard = (CardView) findViewById(R.id.assessmentCV);
        devEngCard = (CardView) findViewById(R.id.learnCV);
        revCard = (CardView) findViewById(R.id.reviseCard);
        speakCard = (CardView) findViewById(R.id.speakCard);



        speakCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridViewActivity.this, AskMeChatActivity.class);
                Uri.parse(ServiceGenerator.API_BASE_URL  + "?client_id=" + clientId +"?client_secret" + clientSecret);
                startActivity(intent);
            }
        });
        assessCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridViewActivity.this, AssessmentChatActivity.class);
                startActivity(intent);
            }
        });
        devEngCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                              CardView devEngCard =(CardView) view;
                Toast.makeText(GridViewActivity.this, "Card is currently locked!" , Toast.LENGTH_SHORT ).show();

            }
        });
        revCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GridViewActivity.this,RevisionChatActivity.class);
                startActivity(intent);

            }
        });
    }

    private void showActivity() {
        Intent intent = new Intent(GridViewActivity.this, StoriesActivity.class);
        startActivity(intent);
    }

    private void sendToken(JsonObject mytoken) {


        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {

            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest = chain.request().newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Accept", "application/json")
                      //  .header("Authorization", tokenType + myToken + Credentials.basic(clientId,clientSecret))
                        .build();
                return chain.proceed(newRequest);
            }
        });

        OkHttpClient client = okHttpClient.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://convai.mcgroce.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        AskMeChatApi service = retrofit.create(AskMeChatApi.class);

        Call<TokenResponsePredict> accessTokenCall = service.getAccessToken_Predict(mytoken);
        accessTokenCall.enqueue(new Callback<TokenResponsePredict>() {
            @Override
            public void onResponse(Call<TokenResponsePredict> call, Response<TokenResponsePredict> response) {

                if (!response.toString().equals("") || response.isSuccessful()) {
                    TokenResponsePredict jsonResponse = response.body();

                    if (jsonResponse != null) {
                       // myToken = jsonResponse.getAccess_token_predict();
                        //tokenType = jsonResponse.getToken_type_predict();
                        //expires_in = jsonResponse.getExpires_in_predict();

                    }

                }
            };

            @Override
            public void onFailure(Call<TokenResponsePredict> call, Throwable t) {
                Toast.makeText(GridViewActivity.this,"Response error",Toast.LENGTH_LONG).show();

            }
        });

    }

}

