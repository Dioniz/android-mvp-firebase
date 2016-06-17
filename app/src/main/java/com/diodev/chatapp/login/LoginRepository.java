package com.diodev.chatapp.login;


public interface LoginRepository {
    void checkSession();
    void signUp(String email, String password);
    void signIn(String email, String password);
}
