package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/21/17.
 */

public class Chat_model {
    private String mMessages;
    private Object mTimeStamp;
    private String mPhotoUrl;
    private String senderId;

    public Chat_model() {
    }

    public Chat_model(String mMessages, Object mTimeStamp, String mPhotoUrl, String senderId) {
        this.mMessages = mMessages;
        this.mTimeStamp = mTimeStamp;
        this.mPhotoUrl = mPhotoUrl;
        this.senderId = senderId;
    }

    public Chat_model(String senderId, Object mTimeStamp, String mMessages) {
        this.senderId = senderId;
        this.mTimeStamp = mTimeStamp;
        this.mMessages = mMessages;
    }

    public String getmMessages() {
        return mMessages;
    }

    public void setmMessages(String mMessages) {
        this.mMessages = mMessages;
    }

    public Object getmTimeStamp() {
        return mTimeStamp;
    }

    public void setmTimeStamp(Object mTimeStamp) {
        this.mTimeStamp = mTimeStamp;
    }

    public String getmPhotoUrl() {
        return mPhotoUrl;
    }

    public void setmPhotoUrl(String mPhotoUrl) {
        this.mPhotoUrl = mPhotoUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
}
