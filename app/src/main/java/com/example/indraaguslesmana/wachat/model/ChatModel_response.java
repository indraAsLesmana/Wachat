package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 3/7/17.
 */

public class ChatModel_response {
    private String name;
    private String uid;
    private ChatModel_response.messages messages;

    public ChatModel_response() {
    }

    public ChatModel_response(String name, String uid, ChatModel_response.messages messages) {
        this.name = name;
        this.uid = uid;
        this.messages = messages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public ChatModel_response.messages getMessages() {
        return messages;
    }

    public void setMessages(ChatModel_response.messages messages) {
        this.messages = messages;
    }

    public class messages {
        private String message;
        private String sender_id;
        private Object time_stamp;

        public messages() {
        }

        public messages(String message, String sender_id, Object time_stamp) {
            this.message = message;
            this.sender_id = sender_id;
            this.time_stamp = time_stamp;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getSender_id() {
            return sender_id;
        }

        public void setSender_id(String sender_id) {
            this.sender_id = sender_id;
        }

        public Object getTime_stamp() {
            return time_stamp;
        }

        public void setTime_stamp(Object time_stamp) {
            this.time_stamp = time_stamp;
        }
    }
}
