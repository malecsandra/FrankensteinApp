package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.AppointmentTestSet;
import com.puskin.frankenstein.models.AppointmentTreatment;

import java.util.ArrayList;

/**
 * Created by Alexandra on 15-May-16.
 */
public class TreatmentListEvent {
    ArrayList<AppointmentTreatment> treatments;
    int responseCode;
    String responseMessage;

    public TreatmentListEvent() {
    }

    public TreatmentListEvent(ArrayList<AppointmentTreatment> treatments, int responseCode, String responseMessage) {
        this.treatments = treatments;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ArrayList<AppointmentTreatment> getTreatments() {
        return treatments;
    }

    public void setTreatments(ArrayList<AppointmentTreatment> treatments) {
        this.treatments = treatments;
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
