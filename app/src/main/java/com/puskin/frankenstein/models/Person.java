package com.puskin.frankenstein.models;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by Alexandra on 02-Apr-16.
 */
public class Person extends RealmObject {
    @PrimaryKey
    @SerializedName("PersonId")
    private int personId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Surname")
    private String surname;

    @SerializedName("DigitCode")
    private String digitCode;

    @SerializedName("BirthDate")
    private Date birthDate;

    @SerializedName("Sex")
    private String sex;

    @SerializedName("Email")
    private String email;

    @SerializedName("PhoneNumber")
    private String phoneNo;
    //photo


    public Person() {
    }

    public Person(int personId, String name, String surname, String digitCode, Date birthDate, String sex, String email, String phoneNo) {
        this.personId = personId;
        this.name = name;
        this.surname = surname;
        this.digitCode = digitCode;
        this.birthDate = birthDate;
        this.sex = sex;
        this.email = email;
        this.phoneNo = phoneNo;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
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

    public String getDigitCode() {
        return digitCode;
    }

    public void setDigitCode(String digitCode) {
        this.digitCode = digitCode;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }
}
