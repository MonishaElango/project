package com.hertzai.hevolve.models.appModel;

import java.util.ArrayList;

public class Revision_No {
    public String question;
    public ArrayList<String> message;
    public Boolean isReply;
    public ArrayList<String> optionList = null ;
    public String video_link;

    public Revision_No(String question, ArrayList<String> message, Boolean isReply, ArrayList<String> optionList ,String video_link ) {
        this.question = question;
        this.message = message;
        this.isReply = isReply;
        this.optionList = optionList;
        this.video_link=video_link;
    }

    public String getVideo_url() {
        return video_link;
    }

    public void setVideo_url(String video_url) {
        this.video_link = video_url;
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

    public ArrayList<String> getOptionList() {
        return optionList;
    }

    public void setOptionList(ArrayList<String> optionList) {
        this.optionList = optionList;
    }
}
