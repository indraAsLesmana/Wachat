package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/21/17.
 */

public class Chat_model {
    private String mMessages;
    private Object mTimeStamp;
    private String mPhotoUrl;

    public Chat_model() {
    }

    public Chat_model(String mMessages, Object mTimeStamp, String mPhotoUrl) {
        this.mMessages = mMessages;
        this.mTimeStamp = mTimeStamp;
        this.mPhotoUrl = mPhotoUrl;
    }

    public Chat_model(String mMessages, Object mTimeStamp) {
        this.mMessages = mMessages;
        this.mTimeStamp = mTimeStamp;
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
}
