package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alexandra on 07-May-16.
 */
public class AppointmentModel {
    @SerializedName("AppointmentId")
    private int appointmentId;
    @SerializedName("PersonId")
    private int personId;
    @SerializedName("DoctorId")
    private int doctorId;
    @SerializedName("AppointmentDate")
    private Date appointmentDate;
    @SerializedName("StatusId")
    private int statusId;
    @SerializedName("Doctor")
    private String doctor;
    @SerializedName("Speciality")
    private String speciality;
    @SerializedName("ClinicName")
    private String clinicName;
    @SerializedName("ClinicAddress")
    private String clinicAddress;

    @SerializedName("AppointmentDetailsList")
    private ArrayList<AppointmentDetailsModel> appointmentDetailsList;

    public AppointmentModel() {
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
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

    public String getClinicName() {
        return clinicName;
    }

    public void setClinicName(String clinicName) {
        this.clinicName = clinicName;
    }

    public String getClinicAddress() {
        return clinicAddress;
    }

    public void setClinicAddress(String clinicAddress) {
        this.clinicAddress = clinicAddress;
    }

    public ArrayList<AppointmentDetailsModel> getAppointmentDetailsList() {
        return appointmentDetailsList;
    }

    public void setAppointmentDetailsList(ArrayList<AppointmentDetailsModel> appointmentDetailsList) {
        this.appointmentDetailsList = appointmentDetailsList;
    }
}
