package com.example.indraaguslesmana.wachat;

import com.example.indraaguslesmana.wachat.login.LoginService;

/**
 * Created by indraaguslesmana on 2/13/17.
 */

public enum Dependencies {
    INSTANCE;

    private LoginService loginService;
    private String firebaseToken;
    //private Config config;

}
