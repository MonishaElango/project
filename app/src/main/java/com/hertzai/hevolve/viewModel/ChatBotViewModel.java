package com.hertzai.hevolve.viewModel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.hertzai.hevolve.api.AskMeChatApi;
import com.hertzai.hevolve.gson.AskMeAssis;
import com.hertzai.hevolve.gson.Assess_ResponseMessage;
import com.hertzai.hevolve.gson.Revision_Response_Message;
import com.hertzai.hevolve.managers.RetrofitManager;
import com.hertzai.hevolve.models.ErrorModel;
import com.hertzai.hevolve.models.appModel.HevolveApp;
import com.hertzai.hevolve.models.gson.User;

import java.util.Objects;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class ChatBotViewModel extends HevolveBaseViewModel{

    private MutableLiveData<AskMeAssis> speakBookLiveData = new MutableLiveData<AskMeAssis>();
    private MutableLiveData<Assess_ResponseMessage> assessLiveData = new MutableLiveData<>();
private MutableLiveData<Revision_Response_Message> revisionLiveData = new MutableLiveData<Revision_Response_Message>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    protected MutableLiveData<ErrorModel> errorModelLiveData = new MutableLiveData<>();

    public ChatBotViewModel(@NonNull Application application) {
        super(application);
    }

    public void speakBook(JsonObject gsonObject)
    {
        RetrofitManager.getInstance(getApplication()).getBook().getReply(gsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<AskMeAssis>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("OnSubscribe","On Subscribe started");
            }


            @Override
            public void onError(Throwable e) {
                errorData.postValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onNext(AskMeAssis response) {

                Log.d("on Success","On next started");

                speakBookLiveData.postValue(response);
                HevolveApp.setSpeakBook(getApplication(), (AskMeAssis) response);
            }
        });
    }



    public void Revision(JsonObject gsonObject)
    {
        RetrofitManager.getInstance(getApplication()).getRevision().getReply(gsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Revision_Response_Message>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d("OnSubscribe","On Subscribe started");
            }

            @Override
            public void onNext(Revision_Response_Message revision_response_message) {
                Log.d("on Next","On next started");

                revisionLiveData.postValue(revision_response_message);
                HevolveApp.setRevision(getApplication(), (Revision_Response_Message) revision_response_message );
            }

            @Override
            public void onError(Throwable e) {
                errorData.postValue(e.getMessage());
            }

            @Override
            public void onComplete() {

            }
        });
    }

    public MutableLiveData<AskMeAssis> getSpeakBookLiveData ()
    {
        return speakBookLiveData;
    }
public MutableLiveData<Revision_Response_Message> getRevisionLiveData()
{
    return revisionLiveData;
}
public MutableLiveData<ErrorModel> getErrorModelLiveData()
{
    return errorModelLiveData;
}


}
