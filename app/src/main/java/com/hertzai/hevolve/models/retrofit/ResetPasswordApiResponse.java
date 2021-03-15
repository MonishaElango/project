package com.hertzai.hevolve.models.retrofit;

import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.retrofit.BaseApiResponse;

public class ResetPasswordApiResponse extends BaseApiResponse {

    @SerializedName("access_token")
    private String accessToken;
    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }




}
