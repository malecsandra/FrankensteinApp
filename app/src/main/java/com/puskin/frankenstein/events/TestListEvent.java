package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.AppointmentTestSet;

import java.util.ArrayList;

/**
 * Created by Alexandra on 07-May-16.
 */
public class TestListEvent {
    ArrayList<AppointmentTestSet> testSets;
    int responseCode;
    String responseMessage;

    public TestListEvent(ArrayList<AppointmentTestSet> testSets, int responseCode, String responseMessage) {
        this.testSets = testSets;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ArrayList<AppointmentTestSet> getTestSets() {
        return testSets;
    }

    public void setTestSets(ArrayList<AppointmentTestSet> testSets) {
        this.testSets = testSets;
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
