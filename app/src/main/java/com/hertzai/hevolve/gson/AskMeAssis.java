package com.hertzai.hevolve.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import com.hertzai.hevolve.models.gson.User;
import com.hertzai.hevolve.models.retrofit.BaseApiResponse;

public class AskMeAssis extends BaseApiResponse {
    @SerializedName("history")
    private String history;
    public String getVideo_link() {
        return video_link;
    }
    public void setVideo_link(String video_link) {
        this.video_link = video_link;
    }
    @SerializedName("text")
    private String text;
    @SerializedName("video_link")
    private String video_link ;

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getText() {
        return text;
    }


    public void setText(String text) {
        this.text = text;
    }

    public static String toJson(AskMeAssis speakBook) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(speakBook);
    }

    public static AskMeAssis parse(String userData) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(userData, AskMeAssis.class);
    }
}
