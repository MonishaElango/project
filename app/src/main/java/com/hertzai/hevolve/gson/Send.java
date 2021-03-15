package com.hertzai.hevolve.gson;

import com.google.gson.annotations.SerializedName;

public class Send {
    @SerializedName("text")
    public String text;
    @SerializedName("assessment")
    public String  assessment;
@SerializedName("question_no")
    public String question_no;
@SerializedName("userid")
public String userid;


    public Send(String text, String assessment, String question_no, String userid) {
        this.text = text;
        this.assessment = assessment;
        this.question_no = question_no;
        this.userid = userid;
    }

}

