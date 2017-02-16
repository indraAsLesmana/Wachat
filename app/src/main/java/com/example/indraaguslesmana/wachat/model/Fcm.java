package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/13/17.
 */

public class Fcm {
    private String enabled;
    private String token;

    public Fcm() {
    }

    public Fcm(String enabled, String token) {
        this.enabled = enabled;
        this.token = token;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
