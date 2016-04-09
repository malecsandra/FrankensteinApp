package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.Doctor;

import java.util.ArrayList;

import io.realm.RealmList;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class DoctorEvent {
    private int responseCode;
    private String responseMessage;
    private RealmList<Doctor> doctors;

    public DoctorEvent(int responseCode, String responseMessage, RealmList<Doctor> doctors) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.doctors = doctors;
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

    public RealmList<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(RealmList<Doctor> doctors) {
        this.doctors = doctors;
    }
}
