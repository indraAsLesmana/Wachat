package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 3/2/17.
 */

public class Chat_recent_model {

    //di ambil dari chatModel
    private String message;     // view last Message
    private Object time_stamp;  // view time last message

    //di ambil dari uid Target
    private long lastSeen;      // this indicator is user Online
    private String name;        // for user target name

    public Chat_recent_model() {
    }

    public Chat_recent_model(String message, Object time_stamp, long lastSeen, String name) {
        this.message = message;
        this.time_stamp = time_stamp;
        this.lastSeen = lastSeen;
        this.name = name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(Object time_stamp) {
        this.time_stamp = time_stamp;
    }

    public long getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(long lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
