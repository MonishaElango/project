package com.hertzai.hevolve.models.retrofit;

import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.gson.User;

public class SignInApiResponse {
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
