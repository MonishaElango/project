package com.hertzai.hevolve.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

public class Options {
    @SerializedName("id")
    int id;
    @SerializedName("option")
    String option;
    @SerializedName("created_at")
    String createdAt;
    @SerializedName("updated_at")
    String updateAt;
    @SerializedName("transient_is_selected")
    boolean isSelected;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    public static Options parse(String optionData) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(optionData, Options.class);
    }

    public static String toJson(Options options) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(options);
    }


}
