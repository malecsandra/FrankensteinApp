package com.puskin.frankenstein.models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import io.realm.RealmObject;

/**
 * Created by rakatan on 11.05.2016.
 */
public class AlarmModel extends RealmObject {
    private String alarmID;
    private String drugName;
    private Date startDate;
    private int doses;
    private int currentDose;
    private int periodicity;
    private int periodicityMeasure;
    private boolean showDetails;

    public AlarmModel() {
    }

    public String getAlarmID() {
        return alarmID;
    }

    public void setAlarmID(String alarmID) {
        this.alarmID = alarmID;
    }

    public String getDrugName() {
        return drugName;
    }

    public void setDrugName(String drugName) {
        this.drugName = drugName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public int getDoses() {
        return doses;
    }

    public void setDoses(int doses) {
        this.doses = doses;
    }

    public int getCurrentDose() {
        return currentDose;
    }

    public void setCurrentDose(int currentDose) {
        this.currentDose = currentDose;
    }

    public int getPeriodicity() {
        return periodicity;
    }

    public void setPeriodicity(int periodicity) {
        this.periodicity = periodicity;
    }

    public int getPeriodicityMeasure() {
        return periodicityMeasure;
    }

    public void setPeriodicityMeasure(int periodicityMeasure) {
        this.periodicityMeasure = periodicityMeasure;
    }

    public boolean isShowDetails() {
        return showDetails;
    }

    public void setShowDetails(boolean showDetails) {
        this.showDetails = showDetails;
    }

    public Date calculateNextAlarm(){
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startDate);
        calendar.add(periodicityMeasure, currentDose * periodicity);

        return calendar.getTime();
    }
}
