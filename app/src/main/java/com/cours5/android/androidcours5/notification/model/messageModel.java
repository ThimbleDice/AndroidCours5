package com.cours5.android.androidcours5.notification.model;

public class messageModel {
    private String message;
    private String sender;

    public messageModel() {
    }

    public messageModel(String message, String sender) {
        this.message = message;
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public String getSender() {
        return sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
