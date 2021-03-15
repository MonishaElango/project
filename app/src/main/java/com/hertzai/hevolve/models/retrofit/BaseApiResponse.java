package com.hertzai.hevolve.models.retrofit;

import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.ErrorModel;


public class BaseApiResponse {


    @SerializedName("success")
    private boolean success;

    @SerializedName("error")
    private ErrorModel errorModel;

    public boolean isSuccess() {
        return success;
    }

    public ErrorModel getErrorModel() {
        return errorModel;
    }


    public void setErrorModel(ErrorModel errorModel) {
        this.errorModel = errorModel;
    }
}
