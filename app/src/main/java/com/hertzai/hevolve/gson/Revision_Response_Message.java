package com.hertzai.hevolve.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Revision_Response_Message {
    @SerializedName("subject")
    private String subject;
    @SerializedName("user_id")
    private String user_id;
    @SerializedName("question_no")
    private String questionNo;

    @SerializedName("video_link")
    private String video_link;


    public String getVideo_link() {
        return video_link;
    }


    public String getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(String questionNo) {
        this.questionNo = questionNo;
    }

    @SerializedName("text")
    private ArrayList<String> answerList;
    @SerializedName("options")
    private ArrayList<String> optionLists;

    public Revision_Response_Message(String subject, String user_id,  ArrayList<String> answerList, ArrayList<String> optionLists , String video_link) {
        this.subject = subject;
        this.user_id = user_id;
        this.answerList = answerList;
        this.optionLists = optionLists;
        this.video_link=video_link;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }


    public ArrayList<String> getAnswerList() {
        return answerList;
    }

    public void setAnswerList(ArrayList<String> answerList) {
        this.answerList = answerList;
    }

    public ArrayList<String> getOptionLists() {
        return optionLists;
    }

    public void setOptionLists(ArrayList<String> optionLists) {
        this.optionLists = optionLists;
    }


    public static String toJson(Revision_Response_Message revision_response_message) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.toJson(revision_response_message);
    }

    public static Revision_Response_Message parse(String userData) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        return gson.fromJson(userData, Revision_Response_Message.class);
    }


}
