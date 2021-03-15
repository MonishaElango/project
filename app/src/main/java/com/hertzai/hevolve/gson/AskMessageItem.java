package com.hertzai.hevolve.gson;

public class AskMessageItem {


    public String message;
    public Boolean isReply;
    public String video_link ;


    public AskMessageItem(String message, Boolean isReply , String video_link) {
        this.message = message;
        this.isReply = isReply;
        this.video_link=video_link;
    }
}
