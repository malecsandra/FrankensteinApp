package com.puskin.frankenstein.events;

/**
 * Created by Alexandra on 29-May-16.
 */
public class ShowImageEvent {
    private int doctorID;

    public ShowImageEvent(int doctorID) {
        this.doctorID = doctorID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }
}
