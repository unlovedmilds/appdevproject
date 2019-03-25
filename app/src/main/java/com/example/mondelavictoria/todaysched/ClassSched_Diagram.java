package com.example.mondelavictoria.todaysched;

/**
 * Created by Mon Dela Victoria on 9/10/2018.
 */

public class ClassSched_Diagram extends SuperClass_Scheduler{
    private int id;
    private String subject;

    // Kining Attributes sa obos ako na sya gamiton para mag check sa conflict schedule
    private String hourStart;
    private String formatStart;
    private String  hourEnd;
    private String formatEnd;
    private int startTimeFormat;
    private int endTimeFormat;


    public ClassSched_Diagram(){}


    public ClassSched_Diagram(int id,String subject,String location, String startTime, String endTime, String date, String alertTime, String hourStart,
                              String formatStart, String hourEnd, String formatEnd, int startTimeFormat, int endTimeFormat, String status,String year,String semester){
        super(location,startTime,endTime,date,alertTime,status,year,semester);
            this.id = id;
            this.subject = subject;
            this.hourStart = hourStart;
            this.formatStart = formatStart;
            this.hourEnd = hourEnd;
            this.formatEnd =formatEnd;
            this.startTimeFormat = startTimeFormat;
            this.endTimeFormat = endTimeFormat;
        }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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
}
