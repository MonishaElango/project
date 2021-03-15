package com.hertzai.hevolve.api;


import com.hertzai.hevolve.gson.Assess_ResponseMessage;
import com.google.gson.JsonObject;
import com.hertzai.hevolve.gson.TokenResponseAssessment;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AssessChatApi {

    @POST("http://assess.mcgroce.com")
    @Headers("Content-Type: application/json")
    Call<Assess_ResponseMessage> getReply(@Body JsonObject jsonObject);

    @POST("oauth2/token")
    @Headers({"Content-Type: application/json" , "Accept: application/json"})
    Call<TokenResponseAssessment> getAccessToken_Assessment(@Body JsonObject jsonObject);

}

