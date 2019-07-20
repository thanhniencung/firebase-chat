package com.c4f.firebase_chat.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.ServerValue;

import java.util.Map;

public class Message {
    private String content;
    private User owner;
    private Long timestamp;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Map<String,String> getTimestamp() {
        return ServerValue.TIMESTAMP;
    }

    @Exclude
    public long getTime() {
        return (long) timestamp;
    }

}
