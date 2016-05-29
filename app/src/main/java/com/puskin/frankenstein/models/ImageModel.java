package com.puskin.frankenstein.models;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Alexandra on 29-May-16.
 */
public class ImageModel {
    @SerializedName("DoctorId")
    private int doctorID;
    @SerializedName("Photo")
    private String photo;

    public ImageModel(int doctorID, String photo) {
        this.doctorID = doctorID;
        this.photo = photo;
    }

    public int getDoctorID() {
        return doctorID;
    }

    public void setDoctorID(int doctorID) {
        this.doctorID = doctorID;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Bitmap getImageAsBitmap(){

        byte[] decodedString = Base64.decode(photo, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
