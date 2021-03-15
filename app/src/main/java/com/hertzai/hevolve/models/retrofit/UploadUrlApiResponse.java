package com.hertzai.hevolve.models.retrofit;

import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.retrofit.BaseApiResponse;

public class UploadUrlApiResponse extends BaseApiResponse {
    @SerializedName("presigned_url")

    private String preSignedUrl;
    @SerializedName("photo_path")
    private String photoPath;

    public String getPreSignedUrl() {
        return preSignedUrl;
    }

    public String getPhotoPath() {
        return photoPath;
    }


}
