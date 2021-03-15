package com.hertzai.hevolve.api;

import com.google.gson.JsonObject;

import com.hertzai.hevolve.gson.Revision_Response_Message;
import com.hertzai.hevolve.gson.TokenResponseRevision;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RevisionChatApi {
    @POST("revision")
    @Headers({"Content-Type: application/json"})
    Observable<Revision_Response_Message> getReply(@Body JsonObject jsonObject);

    @POST("revision/oauth2/token")
    @Headers({"Content-Type: application/json" , "Accept: application/json"})
    Call<TokenResponseRevision> getAccessToken_Revision(@Body JsonObject jsonObject);
}
