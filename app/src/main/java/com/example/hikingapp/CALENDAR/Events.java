package com.example.hikingapp.CALENDAR;

public class Events {
    String EVENT,TIME,DATE,MOUNT,YEAR;

    public String getEVENT() {
        return EVENT;
    }

    public void setEVENT(String EVENT) {
        this.EVENT = EVENT;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public String getDATE() {
        return DATE;
    }

    public void setDATE(String DATE) {
        this.DATE = DATE;
    }

    public String getMOUNT() {
        return MOUNT;
    }

    public void setMOUNT(String MOUNT) {
        this.MOUNT = MOUNT;
    }

    public String getYEAR() {
        return YEAR;
    }

    public void setYEAR(String YEAR) {
        this.YEAR = YEAR;
    }

    public Events(String EVENT, String TIME, String DATE, String MOUNT, String YEAR) {
        this.EVENT = EVENT;
        this.TIME = TIME;
        this.DATE = DATE;
        this.MOUNT = MOUNT;
        this.YEAR = YEAR;
    }
}
