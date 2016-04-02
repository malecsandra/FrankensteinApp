package com.puskin.frankenstein.events;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class LoginEvent {
    private int responseCode;
    private String respondeMessage;

    public LoginEvent(int responseCode, String respondeMessage) {
        this.responseCode = responseCode;
        this.respondeMessage = respondeMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getRespondeMessage() {
        return respondeMessage;
    }

    public void setRespondeMessage(String respondeMessage) {
        this.respondeMessage = respondeMessage;
    }
}
