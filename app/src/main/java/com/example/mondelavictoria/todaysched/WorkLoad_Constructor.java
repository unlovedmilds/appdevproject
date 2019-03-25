package com.example.mondelavictoria.todaysched;

/**
 * Created by Rey Dela Victoria on 8/29/2018.
 */

public class WorkLoad_Constructor {
    int id;
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


    public int getStartTimeFormat() {
        return startTimeFormat;
    }

    public void setStartTimeFormat(int startTimeFormat) {
        this.startTimeFormat = startTimeFormat;
    }

    public int getEndTimeFormat() {
        return endTimeFormat;
    }

    public void setEndTimeFormat(int entTimeFormat) {
        this.endTimeFormat = entTimeFormat;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public WorkLoad_Constructor(){}

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



    public String getFormatStart() {
        return formatStart;
    }

    public void setFormatStart(String formatStart) {
        this.formatStart = formatStart;
    }



    public String getHourStart() {
        return hourStart;
    }

    public void setHourStart(String hourStart) {
        this.hourStart = hourStart;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getAlertTime() {
        return alertTime;
    }

    public void setAlertTime(String alertTime) {
        this.alertTime = alertTime;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public WorkLoad_Constructor(int id, String dailyTask, String location, String startTime, String endTime, String date,
                                  String alertTime, String hourStart, String formatStart,String hourEnd, String formatEnd,int startTimeFormat,
                                  int endTimeFormat, String status) {
        this.id = id;
        this.dailyTask = dailyTask;
        this.location = location;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.alertTime = alertTime;
        this.hourStart = hourStart;
        this.formatStart = formatStart;
        this.hourEnd = hourEnd;
        this.formatEnd = formatEnd;
        this.startTimeFormat = startTimeFormat;
        this.endTimeFormat = endTimeFormat;
        this.status = status;
    }



}
