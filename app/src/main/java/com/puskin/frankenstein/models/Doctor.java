package com.puskin.frankenstein.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Doctor extends RealmObject implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.doctorId);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeInt(this.specialityId);
        dest.writeInt(this.locationId);
        dest.writeString(this.stamp);
        dest.writeString(this.email);
        dest.writeString(this.phoneNumber);
        dest.writeParcelable(this.speciality, flags);
        dest.writeParcelable(this.clinic, flags);
    }

    protected Doctor(Parcel in) {
        this.doctorId = in.readInt();
        this.name = in.readString();
        this.surname = in.readString();
        this.specialityId = in.readInt();
        this.locationId = in.readInt();
        this.stamp = in.readString();
        this.email = in.readString();
        this.phoneNumber = in.readString();
        this.speciality = in.readParcelable(Speciality.class.getClassLoader());
        this.clinic = in.readParcelable(Clinic.class.getClassLoader());
    }

    public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
        @Override
        public Doctor createFromParcel(Parcel source) {
            return new Doctor(source);
        }

        @Override
        public Doctor[] newArray(int size) {
            return new Doctor[size];
        }
    };
}
