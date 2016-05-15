package com.puskin.frankenstein.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexandra on 15-May-16.
 */
public class AppointmentTreatment implements ParentListItem {
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("TreatmentDate")
    private Date treatmentDate;
    @SerializedName("Diagnostic")
    private String diagnostic;
    @SerializedName("Doctor")
    private String doctorName;
    @SerializedName("Speciality")
    private String specialityName;
    @SerializedName("TreatmentList")
    private ArrayList<TreatmentDetailModel> treatmentList;

    public AppointmentTreatment() {
    }


    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getTreatmentDate() {
        return treatmentDate;
    }

    public void setTreatmentDate(Date treatmentDate) {
        this.treatmentDate = treatmentDate;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public ArrayList<TreatmentDetailModel> getTreatmentList() {
        return treatmentList;
    }

    public void setTreatmentList(ArrayList<TreatmentDetailModel> treatmentList) {
        this.treatmentList = treatmentList;
    }

    @Override
    public List<?> getChildItemList() {
        return treatmentList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
