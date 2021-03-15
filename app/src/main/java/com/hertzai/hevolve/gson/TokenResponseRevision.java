package com.hertzai.hevolve.gson;

import com.google.api.client.json.JsonString;
import com.google.gson.annotations.SerializedName;

public class TokenResponseRevision {

    @SerializedName("TokenName")
    private String TokenName;
    private String refresh_token;
   @SerializedName("TokenType")
    private String TokenType;
    @SerializedName("expires_in")
    private int expires_in;
    @SerializedName("AccessToken")
    private String AccessToken;

    public String getTokenName() {
        return TokenName;
    }

    public void setTokenName(String tokenName) {
        TokenName = tokenName;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getTokenType() {
        return TokenType;
    }

    public void setTokenType(String tokenType) {
        TokenType = tokenType;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String accessToken) {
        AccessToken = accessToken;
    }
}
