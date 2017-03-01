package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/21/17.
 */

public class Chat_model2 {
    private String mMessages;
    private String mTimeStamp;
    private String senderId;

    public Chat_model2() {
    }

    public Chat_model2(String mMessages, String mTimeStamp, String senderId) {
        this.mMessages = mMessages;
        this.mTimeStamp = mTimeStamp;
        this.senderId = senderId;
    }

    public String getmMessages() {
        return mMessages;
    }

    public void setmMessages(String mMessages) {
        this.mMessages = mMessages;
    }

    public String getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(String mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
