package com.hertzai.hevolve.api;

import com.google.gson.JsonObject;
import com.hertzai.hevolve.gson.AskMeAssis;
import com.hertzai.hevolve.restService.AccessToken;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface TokenAuthApi {

    @POST("revision/oauth2/token")
    @Headers("Content-type: application/json")
    Call<AccessToken> getAccessToken(
    @Field("client_id") String clientID,
   @Field("client_secret") String clientSecret);

}
