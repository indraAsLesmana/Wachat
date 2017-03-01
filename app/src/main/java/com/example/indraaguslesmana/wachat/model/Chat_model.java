package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/21/17.
 */

public class Chat_model {
    private String messages;
    private Object time_stamp;
    private String sender_id;

    public Chat_model() {
    }

    public Chat_model(String messages, Object time_stamp, String sender_id) {
        this.messages = messages;
        this.time_stamp = time_stamp;
        this.sender_id = sender_id;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public Object getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Object time_stamp) {
        this.time_stamp = time_stamp;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }
}
