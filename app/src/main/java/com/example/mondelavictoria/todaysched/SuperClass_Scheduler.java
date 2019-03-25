package com.example.mondelavictoria.todaysched;

/**
 * Created by Rey Dela Victoria on 9/10/2018.
 */

public class SuperClass_Scheduler {


    private String location;
    private String startTime;
    private String endTime;
    private String date;
    private String alertTime;
    private String status;
    private String year;
    private String semester;

    public SuperClass_Scheduler(){}

    public SuperClass_Scheduler(String location,String startTime, String endTime, String date, String alertTime, String status, String year, String semester) {
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.alertTime = alertTime;
        this.status = status;
        this.year = year;
        this.semester = semester;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
