package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.Clinic;

import io.realm.RealmList;

/**
 * Created by Alexandra on 17-Apr-16.
 */
public class ClinicEvent {
    private int responseCode;
    private String responseMessage;
    private RealmList<Clinic> clinics;

    public ClinicEvent(int responseCode, String responseMessage, RealmList<Clinic> clinics) {
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
        this.clinics = clinics;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public RealmList<Clinic> getClinics() {
        return clinics;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public void setClinics(RealmList<Clinic> clinics) {
        this.clinics = clinics;
    }
}
