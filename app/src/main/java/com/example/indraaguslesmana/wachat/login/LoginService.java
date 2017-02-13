package com.example.indraaguslesmana.wachat.login;

/**
 * Created by indraaguslesmana on 2/13/17.
 */
public interface LoginService {

    // TODO : you must learn RxJava

    void loginWithGoogle(String idToken);

    void loginWithEmailAndPass(String email, String password);

    void sendPasswordResetEmail(String email);
}
