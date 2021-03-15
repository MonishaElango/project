package com.hertzai.hevolve.managers.retrofit;

import com.google.gson.JsonObject;
import com.hertzai.hevolve.models.gson.User;
import com.hertzai.hevolve.models.retrofit.ResetPasswordApiResponse;
import com.hertzai.hevolve.models.retrofit.SignInApiResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface SignLoginApi {


    //Login
    @POST("createstudent")
    Observable<User> signUp(@Body JsonObject gsonObject);

    @POST("verifyStudent")
    Observable<Boolean> login(@Body JsonObject gsonObject);




}
