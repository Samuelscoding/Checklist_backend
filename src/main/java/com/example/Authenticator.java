package com.asc.jira.ext.core;

public interface Authenticator {

    User getLoggedIn(String loginToken);

    void invalidate(String loginToken);

    User login(String username, String password);

}
