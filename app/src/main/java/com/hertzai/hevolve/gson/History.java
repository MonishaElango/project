package com.hertzai.hevolve.gson;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class History {
    @SerializedName("bot_messages")
    private ArrayList<String> botList;
    @SerializedName("user_messages")
    private ArrayList<String> userList;

    public History(ArrayList<String> botList, ArrayList<String> userList) {
        this.botList = botList;
        this.userList = userList;
    }
}
