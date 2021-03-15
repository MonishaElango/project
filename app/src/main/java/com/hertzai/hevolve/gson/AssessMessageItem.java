package com.hertzai.hevolve.gson;


import java.util.ArrayList;

public class AssessMessageItem {
    public ArrayList<String> message;
    public Boolean isReply;

    public AssessMessageItem(ArrayList<String> message, Boolean isReply) {
        this.message = message;
        this.isReply = isReply;
    }
}
