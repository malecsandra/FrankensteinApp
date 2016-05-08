package com.puskin.frankenstein.events;

import com.puskin.frankenstein.models.Doctor;

/**
 * Created by Alexandra on 08-May-16.
 */
public class DoctorClickEvent {
    private Doctor doctor;

    public DoctorClickEvent(Doctor doctor) {
        this.doctor = doctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
