package com.puskin.frankenstein.models;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class LoginObject {
    private String username;
    private String password;

    public LoginObject(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
