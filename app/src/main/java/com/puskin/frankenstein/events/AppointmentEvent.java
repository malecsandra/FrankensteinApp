package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.AppointmentModel;
import com.puskin.frankenstein.models.AppointmentTestSet;

import java.util.ArrayList;

/**
 * Created by Alexandra on 07-May-16.
 */
public class AppointmentEvent {
    ArrayList<AppointmentModel> appointments;
    int responseCode;
    String responseMessage;

    public AppointmentEvent(ArrayList<AppointmentModel> appointments, int responseCode, String responseMessage) {
        this.appointments = appointments;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ArrayList<AppointmentModel> getAppointments() {
        return appointments;
    }

    public void setAppointments(ArrayList<AppointmentModel> appointments) {
        this.appointments = appointments;
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
