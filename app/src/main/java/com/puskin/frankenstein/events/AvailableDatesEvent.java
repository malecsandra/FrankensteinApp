package com.puskin.frankenstein.events;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexandra on 08-May-16.
 */
public class AvailableDatesEvent {
    private ArrayList<Date> availableDates;
    private int responseCode;
    private String responseMessage;

    public AvailableDatesEvent(ArrayList<Date> availableDates, int responseCode, String responseMessage) {
        this.availableDates = availableDates;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ArrayList<Date> getAvailableDates() {
        return availableDates;
    }

    public void setAvailableDates(ArrayList<Date> availableDates) {
        this.availableDates = availableDates;
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
