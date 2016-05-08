package com.puskin.frankenstein.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Speciality extends RealmObject implements Parcelable {
    @PrimaryKey
    @SerializedName("SpecialityId")
    private int specialityId;

    @SerializedName("SpecialityName")
    private String specialityName;

    public int getSpecialityId() {
        return specialityId;
    }

    public void setSpecialityId(int specialityId) {
        this.specialityId = specialityId;
    }

    public String getSpecialityName() {
        return specialityName;
    }

    public void setSpecialityName(String specialityName) {
        this.specialityName = specialityName;
    }

    public Speciality(int specialityId, String specialityName) {
        this.specialityId = specialityId;
        this.specialityName = specialityName;
    }

    public Speciality() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.specialityId);
        dest.writeString(this.specialityName);
    }

    protected Speciality(Parcel in) {
        this.specialityId = in.readInt();
        this.specialityName = in.readString();
    }

    public static final Parcelable.Creator<Speciality> CREATOR = new Parcelable.Creator<Speciality>() {
        @Override
        public Speciality createFromParcel(Parcel source) {
            return new Speciality(source);
        }

        @Override
        public Speciality[] newArray(int size) {
            return new Speciality[size];
        }
    };
}
