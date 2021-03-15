package com.hertzai.hevolve.models.retrofit;

import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.retrofit.BaseApiResponse;



import java.util.ArrayList;

public class ConfigApiResponse extends BaseApiResponse {



    @SerializedName("update_available")
    private Boolean updateAvailable;
    @SerializedName("force_update")
    private Boolean forceUpdate;
    @SerializedName("latest_android_version")
    private String latestAndroidVersion;





    public Boolean getUpdateAvailable() {
        return updateAvailable;
    }

    public Boolean getForceUpdate() {
        return forceUpdate;
    }

    public String getLatestAndroidVersion() {
        return latestAndroidVersion;
    }



}
