package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandra on 15-May-16.
 */
public class TreatmentDetailModel {
    @SerializedName("TreatmentId")
    private int treatmentId;
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("DrugName")
    private String drugName;
    @SerializedName("Tablets")
    private int tablets;
    @SerializedName("Dose")
    private int dose;
    @SerializedName("Periodicity")
    private int periodicity;
    @SerializedName("PeriodicityType")
    private int periodicityType;

    public TreatmentDetailModel() {
    }

    public TreatmentDetailModel(int treatmentId, int appointmentId, String drugName, int tablets, int dose, int periodicity, int periodicityType) {
        this.treatmentId = treatmentId;
        this.appointmentId = appointmentId;
        this.drugName = drugName;
        this.tablets = tablets;
        this.dose = dose;
        this.periodicity = periodicity;
        this.periodicityType = periodicityType;
    }

    public int getTreatmentId() {
        return treatmentId;
    }

    public void setTreatmentId(int treatmentId) {
        this.treatmentId = treatmentId;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public int getTablets() {
        return tablets;
    }

    public void setTablets(int tablets) {
        this.tablets = tablets;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    public int getPeriodicityType() {
        return periodicityType;
    }

    public void setPeriodicityType(int periodicityType) {
        this.periodicityType = periodicityType;
    }
}
