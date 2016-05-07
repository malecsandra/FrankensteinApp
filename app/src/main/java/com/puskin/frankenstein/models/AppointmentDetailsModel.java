package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandra on 07-May-16.
 */
public class AppointmentDetailsModel {
    @SerializedName("AppointmentDetailId")
    private int appointmentdetailId;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("Diagnostic")
    private String diagnostic;
    @SerializedName("Comments")
    private String comments;

    public AppointmentDetailsModel() {
    }

    public AppointmentDetailsModel(int appointmentdetailId, int appointmentId, String diagnostic, String comments) {
        this.appointmentdetailId = appointmentdetailId;
        this.appointmentId = appointmentId;
        this.diagnostic = diagnostic;
        this.comments = comments;
    }

    public int getAppointmentdetailId() {
        return appointmentdetailId;
    }

    public void setAppointmentdetailId(int appointmentdetailId) {
        this.appointmentdetailId = appointmentdetailId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
