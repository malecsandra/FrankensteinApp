package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 09-Apr-16.
 */
public class Speciality extends RealmObject {
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
}
