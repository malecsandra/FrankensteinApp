package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class User extends RealmObject {
    @PrimaryKey
    @SerializedName("UserId")
    private int userId;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("UserPassword")
    private String password;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("StatusId")
    private boolean active;

    @SerializedName("PersonModel")
    private Person person;

    public User() {
    }

    public User(int userId, String userName, String password, String fullName, boolean active, Person person) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.fullName = fullName;
        this.active = active;
        this.person = person;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
