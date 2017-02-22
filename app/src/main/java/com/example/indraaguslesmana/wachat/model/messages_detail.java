package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/16/17.
 */

public class Messages_Detail {
    private String destination;
    private String message;
    private String sender;
    private Object timeStamp;

    public Messages_Detail() {
    }

    public Messages_Detail(String destination, String message, String sender, Object timeStamp) {
        this.destination = destination;
        this.message = message;
        this.sender = sender;
        this.timeStamp = timeStamp;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Object getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Object timeStamp) {
        this.timeStamp = timeStamp;
    }

}
