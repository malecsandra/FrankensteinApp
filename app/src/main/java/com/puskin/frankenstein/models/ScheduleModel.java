package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Alexandra on 08-May-16.
 */
public class ScheduleModel {
    @SerializedName("DoctorId")
    private int doctorID;
    @SerializedName("RequestDate")
    private Date requestDate;

    public ScheduleModel(int doctorID, Date requestDate) {
        this.doctorID = doctorID;
        this.requestDate = requestDate;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public Date getRequestDate() {
        return requestDate;
    }

    public void setRequestDate(Date requestDate) {
        this.requestDate = requestDate;
    }
}
