package com.puskin.frankenstein.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Clinic extends RealmObject implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.locationId);
        dest.writeString(this.clinicName);
        dest.writeString(this.clinicAddress);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.Details);
    }

    protected Clinic(Parcel in) {
        this.locationId = in.readInt();
        this.clinicName = in.readString();
        this.clinicAddress = in.readString();
        this.phoneNumber = in.readString();
        this.Details = in.readString();
    }

    public static final Parcelable.Creator<Clinic> CREATOR = new Parcelable.Creator<Clinic>() {
        @Override
        public Clinic createFromParcel(Parcel source) {
            return new Clinic(source);
        }

        @Override
        public Clinic[] newArray(int size) {
            return new Clinic[size];
        }
    };
}
