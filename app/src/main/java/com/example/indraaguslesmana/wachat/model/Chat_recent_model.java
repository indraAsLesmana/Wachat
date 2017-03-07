package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 3/2/17.
 */

public class Chat_recent_model {
    // get from child Message
    private String message;     // view last Message
    private Object time_stamp;  // view time last message

    //get from child user
    private Object lastSeen;
    private String name;


    public Chat_recent_model() {
    }

    public Chat_recent_model(String message, Object time_stamp, Object lastSeen, String name) {
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

    public Object getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(Object lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class MessageDetail{
        private String message;     // view last Message
        private Object time_stamp;  // view time last message

        public MessageDetail() {
        }

        public MessageDetail(String message, Object time_stamp) {
            this.message = message;
            this.time_stamp = time_stamp;
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
    }

    public static class UserDetail{
        private Object lastSeen;
        private String name;

        public UserDetail() {
        }

        public UserDetail(Object lastSeen, String name) {
            this.lastSeen = lastSeen;
            this.name = name;
        }

        public Object getLastSeen() {
            return lastSeen;
        }

        public void setLastSeen(Object lastSeen) {
            this.lastSeen = lastSeen;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
