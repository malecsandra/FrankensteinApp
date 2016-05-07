package com.puskin.frankenstein.models;

import com.bignerdranch.expandablerecyclerview.Model.ParentListItem;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Alexandra on 07-May-16.
 */
public class AppointmentTestSet implements ParentListItem{
    @SerializedName("AppointmentId")
    private int appointmentID;
    @SerializedName("MedicalTestDate")
    private Date medicalTestDate;
    @SerializedName("Doctor")
    private String doctor;
    @SerializedName("Speciality")
    private String speciality;
    @SerializedName("TestList")
    private ArrayList<MedicalTestModel> testList;

    public AppointmentTestSet(int appointmentID, Date medicalTestDate, String doctor, String speciality, ArrayList<MedicalTestModel> testList) {
        this.appointmentID = appointmentID;
        this.medicalTestDate = medicalTestDate;
        this.doctor = doctor;
        this.speciality = speciality;
        this.testList = testList;
    }

    public int getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(int appointmentID) {
        this.appointmentID = appointmentID;
    }

    public Date getMedicalTestDate() {
        return medicalTestDate;
    }

    public void setMedicalTestDate(Date medicalTestDate) {
        this.medicalTestDate = medicalTestDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public ArrayList<MedicalTestModel> getTestList() {
        return testList;
    }

    public void setTestList(ArrayList<MedicalTestModel> testList) {
        this.testList = testList;
    }

    @Override
    public List<?> getChildItemList() {
        return testList;
    }

    @Override
    public boolean isInitiallyExpanded() {
        return false;
    }
}
