package com.example.mondelavictoria.todaysched;

/**
 * Created by Rey Dela Victoria on 9/5/2018.
 */

public class Scheduler  {


    String dailyTask;
    String location;
    String startTime;
    String endTime;
    String date;
    String alertTime;
    String hourStart;
    String formatStart;
    String  hourEnd;
    String formatEnd;
    int startTimeFormat;
    int endTimeFormat;
    String status;

    public Scheduler(){}
    public Scheduler( String dailyTask, String location, String startTime, String endTime, String date, String alertTime, String hourStart,
                     String formatStart, String hourEnd, String formatEnd, int startTimeFormat, int endTimeFormat, String status) {

        this.dailyTask = dailyTask;
        this.location = location;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.alertTime = alertTime;
        this.hourStart = hourStart;
        this.formatStart = formatStart;
        this.hourEnd = hourEnd;
        this.formatEnd = formatEnd;
        this.startTimeFormat = startTimeFormat;
        this.endTimeFormat = endTimeFormat;
        this.status = status;
    }



    public String getDailyTask() {
        return dailyTask;
    }

    public void setDailyTask(String dailyTask) {
        this.dailyTask = dailyTask;
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

    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }

    public String getFormatStart() {
        return formatStart;
    }

    public void setFormatStart(String formatStart) {
        this.formatStart = formatStart;
    }

    public String getHourEnd() {
        return hourEnd;
    }

    public void setHourEnd(String hourEnd) {
        this.hourEnd = hourEnd;
    }

    public String getFormatEnd() {
        return formatEnd;
    }

    public void setFormatEnd(String formatEnd) {
        this.formatEnd = formatEnd;
    }

    public int getStartTimeFormat() {
        return startTimeFormat;
    }

    public void setStartTimeFormat(int startTimeFormat) {
        this.startTimeFormat = startTimeFormat;
    }

    public int getEndTimeFormat() {
        return endTimeFormat;
    }

    public void setEndTimeFormat(int endTimeFormat) {
        this.endTimeFormat = endTimeFormat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
