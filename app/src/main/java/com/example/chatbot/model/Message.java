package com.example.chatbot.model;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String message;
    private String messdate;

    public Message(String sender, String message, String messdate) {
        this.sender = sender;
        this.message = message;
        this.messdate = messdate;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessdate() {
        return messdate;
    }

    public void setMessdate(String messdate) {
        this.messdate = messdate;
    }

}