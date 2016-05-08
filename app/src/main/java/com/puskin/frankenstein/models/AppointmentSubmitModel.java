package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Alexandra on 08-May-16.
 */
public class AppointmentSubmitModel {
    @SerializedName("AppointmentId")
    private int appointmentID;
    @SerializedName("PersonId")
    private int personID;
    @SerializedName("DoctorId")
    private int doctorID;
    @SerializedName("AppointmentDate")
    private Date appointmentDate;
    @SerializedName("StatusId")
    private int statusID;

    public AppointmentSubmitModel(int appointmentID, int personID, int doctorID, Date appointmentDate, int statusID) {
        this.appointmentID = appointmentID;
        this.personID = personID;
        this.doctorID = doctorID;
        this.appointmentDate = appointmentDate;
        this.statusID = statusID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public int getPersonID() {
        return personID;
    }

    public void setPersonID(int personID) {
        this.personID = personID;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getStatusID() {
        return statusID;
    }

    public void setStatusID(int statusID) {
        this.statusID = statusID;
    }
}
