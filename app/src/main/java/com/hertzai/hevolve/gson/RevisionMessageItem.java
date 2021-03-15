package com.hertzai.hevolve.gson;

import java.util.ArrayList;

public class RevisionMessageItem {

    public ArrayList<String> message;
    public Boolean isReply;

    public RevisionMessageItem(ArrayList<String> message, Boolean isReply) {
        this.message = message;
        this.isReply = isReply;
    }
}
