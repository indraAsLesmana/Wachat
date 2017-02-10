package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/10/17.
 */

public class UserContact {

    private String email;
    private long lastSeen;
    private String name;
    private String uid;

    public UserContact() {
    }

    public UserContact(String email, long lastSeen, String name, String uid) {
        this.email = email;
        this.lastSeen = lastSeen;
        this.name = name;
        this.uid = uid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
