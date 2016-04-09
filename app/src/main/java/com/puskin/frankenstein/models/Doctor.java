package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Doctor extends RealmObject {
    @PrimaryKey
    @SerializedName("DoctorId")
    private int doctorId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Surname")
    private String surname;

    @SerializedName("SpecialityId")
    private int specialityId;

    @SerializedName("LocationId")
    private int locationId;

    @SerializedName("Stamp")
    private String stamp;

    @SerializedName("Email")
    private String email;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("SpecialityModel")
    private Speciality speciality;

    @SerializedName("ClinicModel")
    private Clinic clinic;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public String getStamp() {
        return stamp;
    }

    public void setStamp(String stamp) {
        this.stamp = stamp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Speciality getSpeciality() {
        return speciality;
    }

    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public Doctor(int doctorId, String name, String surname, int specialityId, int locationId, String stamp, String email, String phoneNumber, Speciality speciality, Clinic clinic) {
        this.doctorId = doctorId;
        this.name = name;
        this.surname = surname;
        this.specialityId = specialityId;
        this.locationId = locationId;
        this.stamp = stamp;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.speciality = speciality;
        this.clinic = clinic;
    }

    public Doctor() {
    }
}
