package com.example;

public class LDAPConfig {

    private final String username;
    private final String password;
    private final String url;
    private final String filter;
    private final String searchName;

    public LDAPConfig(String username, String password, String url, String filter, String searchName) {
        this.username = username;
        this.password = password;
        this.url = url;
        this.filter = filter;
        this.searchName = searchName;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUrl() {
        return this.url;
    }

    public String getFilter() {
        return this.filter;
    }

    public String getSearchName() {
        return this.searchName;
    }
}
