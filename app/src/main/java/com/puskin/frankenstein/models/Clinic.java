package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Clinic extends RealmObject {
    @PrimaryKey
    @SerializedName("LocationId")
    private int locationId;

    @SerializedName("ClinicName")
    private String clinicName;

    @SerializedName("ClinicAddress")
    private String clinicAddress;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("Details")
    private String Details;

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        Details = details;
    }

    public Clinic(int locationId, String clinicName, String clinicAddress, String phoneNumber, String details) {
        this.locationId = locationId;
        this.clinicName = clinicName;
        this.clinicAddress = clinicAddress;
        this.phoneNumber = phoneNumber;
        Details = details;
    }

    public Clinic() {
    }
}
