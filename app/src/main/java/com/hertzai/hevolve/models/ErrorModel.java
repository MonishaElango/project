package com.hertzai.hevolve.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class ErrorModel {

    @SerializedName("code")
    int code;
    @SerializedName("message")
    String message;

    public ErrorModel(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorModel(String jsonString, int code) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        if (jsonObject.has("message"))
            message = jsonObject.getString("message");
        else if(jsonObject.has("error"))
            message = jsonObject.getJSONObject("error").getString("message");
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
