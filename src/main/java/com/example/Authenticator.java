package com.example;

public interface Authenticator {

    User getLoggedIn(String loginToken);

    void invalidate(String loginToken);

    User login(String username, String password);

}
