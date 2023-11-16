package com.example;

public class User {

    private final String loginToken;
    private final String name;
    private final String mail;

    public User(String loginToken, String name, String mail) {
        this.loginToken = loginToken;
        this.name = name;
        this.mail = mail;
    }

    public String getLoginToken() {
        return this.loginToken;
    }

    public String getName() {
        return this.name;
    }

    public String getMail() {
        return this.mail;
    }
}
