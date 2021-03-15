package com.hertzai.hevolve.models.appModel;

import java.util.ArrayList;

public class QuestionNo {
    public String question;
    public ArrayList<String> message;
    public Boolean isReply;
    public ArrayList<String> optionList = null ;
    private String video_link;

    public ArrayList<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<String> optionList) {
        this.optionList = optionList;
    }

    public QuestionNo(String question, ArrayList<String> message,ArrayList<String> optionList, Boolean isReply,String video_link) {
        this.question = question;
        this.message = message;
        this.isReply = isReply;
        this.optionList = optionList;
         this.video_link=video_link;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> getMessage() {
        return message;
    }

    public void setMessage(ArrayList<String> message) {
        this.message = message;
    }

    public Boolean getReply() {
        return isReply;
    }

    public void setReply(Boolean reply) {
        isReply = reply;
    }
}
