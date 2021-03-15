package com.hertzai.hevolve.api;

import com.hertzai.hevolve.gson.TokenResponsePredict;
import com.hertzai.hevolve.gson.AskMeAssis;
import com.google.gson.JsonObject;


import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface AskMeChatApi {

    @POST("http://cfb5cd75ea48.ngrok.io")
    Observable <AskMeAssis> getReply(@Body JsonObject jsonObject);

    @POST("predict/oauth2/token")
    @Headers({"Content-Type: application/json" , "Accept: application/json"})
    Call<TokenResponsePredict> getAccessToken_Predict(@Body JsonObject jsonObject);

}
