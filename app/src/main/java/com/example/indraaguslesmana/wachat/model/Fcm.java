package com.example.indraaguslesmana.wachat.model;

/**
 * Created by indraaguslesmana on 2/13/17.
 */

public class Fcm {
    private String Enbled;
    private String token;

    public Fcm(String enbled, String token) {
        Enbled = enbled;
        this.token = token;
    }

    public String getEnbled() {
        return Enbled;
    }

    public void setEnbled(String enbled) {
        Enbled = enbled;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
