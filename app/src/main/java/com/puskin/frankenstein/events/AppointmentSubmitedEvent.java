package com.puskin.frankenstein.events;

/**
 * Created by Alexandra on 08-May-16.
 */
public class AppointmentSubmitedEvent {
    private int responseCode;
    private String responseMessage;

    public AppointmentSubmitedEvent(int responseCode, String responseMessage) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }
}
