package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Alexandra on 07-May-16.
 */
public class MedicalTestModel {

    @SerializedName("MedicalTestId")
    private int medicalTestID;
    @SerializedName("AppointmentId")
    private int appointmentID;
    @SerializedName("Result")
    private String result;
    @SerializedName("TestName")
    private String testName;
    @SerializedName("CategoryName")
    private String categoryName;
    @SerializedName("MinValue")
    private float minValue;
    @SerializedName("MaxValue")
    private float maxValue;
    @SerializedName("UnitMeasure")
    private String unitMeasure;

    public MedicalTestModel(int medicalTestID, int appointmentID, String result, String testName, String categoryName, float minValue, float maxValue, String unitMeasure) {
        this.medicalTestID = medicalTestID;
        this.appointmentID = appointmentID;
        this.result = result;
        this.testName = testName;
        this.categoryName = categoryName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.unitMeasure = unitMeasure;
    }

    public int getMedicalTestID() {
        return medicalTestID;
    }

    public void setMedicalTestID(int medicalTestID) {
        this.medicalTestID = medicalTestID;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public String getUnitMeasure() {
        return unitMeasure;
    }

    public void setUnitMeasure(String unitMeasure) {
        this.unitMeasure = unitMeasure;
    }
}
