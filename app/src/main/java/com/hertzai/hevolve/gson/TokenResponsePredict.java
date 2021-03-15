package com.hertzai.hevolve.gson;

public class TokenResponsePredict {

    public String getAccess_token_predict() {
        return access_token_predict;
    }

    public void setAccess_token_predict(String access_token_predict) {
        this.access_token_predict = access_token_predict;
    }

    public String getRefresh_token_predict() {
        return refresh_token_predict;
    }

    public void setRefresh_token_predict(String refresh_token_predict) {
        this.refresh_token_predict = refresh_token_predict;
    }

    public String getToken_name_predict() {
        return token_name_predict;
    }

    public void setToken_name_predict(String token_name_predict) {
        this.token_name_predict = token_name_predict;
    }

    public String getToken_type_predict() {
        return token_type_predict;
    }

    public void setToken_type_predict(String token_type_predict) {
        this.token_type_predict = token_type_predict;
    }

    public int getExpires_in_predict() {
        return expires_in_predict;
    }

    public void setExpires_in_predict(int expires_in_predict) {
        this.expires_in_predict = expires_in_predict;
    }

    private String access_token_predict;
    private String refresh_token_predict;
    private String token_name_predict;
    private String token_type_predict;
    private int expires_in_predict;
}
