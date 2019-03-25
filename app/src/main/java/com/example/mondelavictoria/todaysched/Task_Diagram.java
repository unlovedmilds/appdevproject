package com.example.mondelavictoria.todaysched;

/**
 * Created by Rey Dela Victoria on 9/10/2018.
 */

public class Task_Diagram extends SuperClass_Scheduler {

    private int id;
    private String task;

    public Task_Diagram() {}

    public Task_Diagram(int ids,String task,String location, String startTime, String endTime, String date, String alertTime, String status, String year, String semester, int id) {
        super(location, startTime, endTime, date, alertTime, status, year, semester);
        this.id = ids;
        this.task = task;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}