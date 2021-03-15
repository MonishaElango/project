package com.hertzai.hevolve.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.gson.JsonObject;
import com.hertzai.hevolve.models.appModel.HevolveApp;
import com.hertzai.hevolve.managers.RetrofitManager;
import com.hertzai.hevolve.models.gson.User;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LoginSignUpViewModel extends HevolveBaseViewModel {

    private MutableLiveData<Boolean> signInLiveData = new MutableLiveData<>();
    private MutableLiveData<User> signUpLiveData = new MutableLiveData<User>();
    private MutableLiveData<String> errorData = new MutableLiveData<>();
    public LoginSignUpViewModel(@NonNull Application application) {
        super(application);
    }

    //User Sign-in call
    public void signIn(JsonObject gsonObject) {
        RetrofitManager.getInstance(getApplication()).getSignLoginApi().login(gsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Boolean user) {
                signInLiveData.postValue(user);
            //    HevolveApp.setUser(getApplication(),user);
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



    //User Sign-Up Call
    public void signUp(JsonObject gsonObject){
        RetrofitManager.getInstance(getApplication()).getSignLoginApi().signUp(gsonObject).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<User>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(User user) {
                signUpLiveData.postValue(user);
                HevolveApp.setUser(getApplication(),user);
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


    public MutableLiveData<Boolean> getSignInLiveData() {
        return signInLiveData;
    }
    public MutableLiveData<User> getSignUpLiveData(){return signUpLiveData;}

    public MutableLiveData<String> getErrorData(){
        return errorData;
    }
}
