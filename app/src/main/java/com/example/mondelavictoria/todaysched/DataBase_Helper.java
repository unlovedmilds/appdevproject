package com.example.mondelavictoria.todaysched;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;

import static com.example.mondelavictoria.todaysched.NotePreviewActivity.getFormattedDate;

/**
 * Created by Mon Dela Victoria on 7/7/2018.
 */

public class DataBase_Helper extends SQLiteOpenHelper {


    public static final String Database_Name = "TodayScheduler_AppDev";
    private static final int DATABASE_VERSION =5;
    AppCompatActivity activity;

    public DataBase_Helper(Context context){
        super(context,Database_Name,null,DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //String sql = ("create table user(ID varchar PRIMARY KEY,Name varchar,Emaill varchar,Password varchar");


        sqLiteDatabase.execSQL("create table Table_Schedule(ID INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, Daily_Task varchar, Location varchar," +
                " Start_Time varchar , End_Time varchar , Day varchar , Alert_Time varchar, Start_Hour varchar ,Format_Start varchar," +
                " End_Hour varchar,Format_End varchar,Start_Time_Format INTEGER,End_Time_Format INTEGER, Status varchar, Year varchar, Semester varchar)");

        sqLiteDatabase.execSQL("create table Table_WorkLoad(ID INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, Company varchar, Location varchar," +
                " Start_Time varchar , End_Time varchar , Day varchar , Alert_Time varchar, Start_Hour varchar ,Format_Start varchar," +
                " End_Hour varchar,Format_End varchar,Start_Time_Format INTEGER,End_Time_Format INTEGER, Status varchar)");

        sqLiteDatabase.execSQL("create table Table_History(ID INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, Daily_Task varchar, Location varchar," +
                " Start_Time varchar , End_Time varchar , Day varchar , Alert_Time varchar, Start_Hour varchar ,Format_Start varchar," +
                " End_Hour varchar,Format_End varchar,Start_Time_Format INTEGER,End_Time_Format INTEGER, Status varchar, Year varchar, Semester varchar)");


        sqLiteDatabase.execSQL("create table Table_Task( What varchar, Location varchar, Start_Date varchar, Start_Time varchar, Month varchar)");

        sqLiteDatabase.execSQL("create table Table_Transition( Transition varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists Table_Schedule");
        sqLiteDatabase.execSQL("drop table if exists Table_WorkLoad");
        sqLiteDatabase.execSQL("drop table if exists Table_Task");
        sqLiteDatabase.execSQL("drop table if exists Table_Transition");
        sqLiteDatabase.execSQL("drop table if exists Table_History");

        onCreate(sqLiteDatabase);
    }

    public boolean insert(String daily, String location, String start_time, String end_time, String day, String alert_time, String start_hour,
                          String format_start, String end_hour, String format_end,int stf,int etf, String status,String year,String semester) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "Select * From Table_Schedule where Day='" + day + "' and Start_Hour='" + start_hour + "'" +
               "and End_Hour='" + end_hour + "' and Format_Start ='" + format_start + "' and Format_End='" + format_end + "'";

        //String sql1 = "SELECT * FROM Table_Schedule WHERE '"+start_hour+"' BETWEEN Start_Hour  AND End_Hour AND Start_Hour != '"+start_hour+"' ";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            return true;

        } else {
            db.execSQL("Insert into Table_Schedule Values(?,'" + daily + "','" + location + "','" + start_time + "'," +
                    "'" + end_time + "','" + day + "','" + alert_time + "','" + start_hour + "','" + format_start + "','" + end_hour + "'," +
                    "'" + format_end + "','" + stf + "','" + etf + "','" + status + "','" + year + "','" + semester + "')");

            return false;
        }

    }

    public boolean insertWorkLoad(String company, String location, String start_time, String end_time, String day, String alert_time, String start_hour,
                          String format_start, String end_hour, String format_end,int stf,int etf, String status) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "Select * From Table_WorkLoad where Day='" + day + "' and Start_Hour='" + start_hour + "'" +
                "and End_Hour='" + end_hour + "' and Format_Start ='" + format_start + "' and Format_End='" + format_end + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            return true;

        } else {
            db.execSQL("Insert into Table_WorkLoad Values(?,'" + company + "','" + location + "','" + start_time + "'," +
                    "'" + end_time + "','" + day + "','" + alert_time + "','" + start_hour + "','" + format_start + "','" + end_hour + "'," +
                    "'" + format_end + "','" + stf + "','" + etf + "','" + status + "')");

            return false;
        }

    }

    public boolean insertTransition(String trans){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Insert into Table_Transition  Values('" + trans + "')";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {

            return true;

        } else {
            return false;
        }
    }

    public Cursor getData(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public Cursor getDataClass(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public Cursor getDataWorkLoad(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }
    public Cursor getDataTransition(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor getDataTask(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql, null);
    }

    public Cursor getAlldataFriday(){
        String friday = "Friday";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Schedule where Day = '"+friday+"' ",null);
        return  cursor;
    }

    public Cursor getAlldataWorkLoadFriday(){
        String friday = "Friday";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_WorkLoad where Day = '"+friday+"' ",null);
        return  cursor;
    }

    public Cursor getAlldataWorkLoadSunday(){
        String sunday = "Sunday";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_WorkLoad where Day = '"+sunday+"' ",null);
        return  cursor;
    }

    public Cursor getAlldataSunday(){
        String sunday = "Sunday";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Schedule where Day = '"+sunday+"'  ",null);
        return  cursor;
    }

    public Cursor getAlldataSaturday(){
        String saturday = "Saturday";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Schedule where Day = '"+saturday+"' ",null);
        return  cursor;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public boolean addDailyTask(MyEventDay myEventDay){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("What", myEventDay.getNote());
        value.put("Location", myEventDay.getLocation());
        value.put("Start_Date", String.valueOf(getFormattedDate(myEventDay.getCalendar().getTime())));
        value.put("Month", myEventDay.getMonth());

        long result;
        result = db.insert("Table_Task",null,value);
        db.close();
        //check wether data is inserted in database
        if(result == -1){
            return false;
        }else{
            return  true;
        }

    }

    public boolean addHistory(ClassHistory_Diagram myEventDay){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("ID", myEventDay.getId());
        value.put("Daily_Task", myEventDay.getSubject());
        value.put("Location", myEventDay.getLocation());
        value.put("Start_Time", myEventDay.getStartTime());
        value.put("End_Time", myEventDay.getEndTime());
        value.put("Start_Time_Format", myEventDay.getStartTimeFormat());
        value.put("End_Time_Format", myEventDay.getEndTimeFormat());
        value.put("Year", myEventDay.getYear());
        value.put("Semester", myEventDay.getSemester());

        long result;
        result = db.insert("Table_History",null,value);
        db.close();
        //check wether data is inserted in database
        if(result == -1){
            return false;
        }else{
            return  true;
        }

    }

    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Schedule",null);
        return  cursor;
    }

    public Cursor getAllDataWork(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Workload",null);
        return  cursor;
    }

    public Cursor getAllDataEvents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * From Table_Task",null);
        return  cursor;
    }

    public Integer deleteDailyTask(){
        SQLiteDatabase db = this.getWritableDatabase();
        //int del = db.delete("Scheduler_Table", "Date=?", new String[]{date});
        int del = db.delete("Table_Schedule", null, null);
        return  del;

    }

    public boolean deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("DELETE FROM Table_Schedule WHERE ID = '"+id+"' ");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0 ){

            return true;
        }else{
            return false;
        }
    }

    public boolean deleteWorkLoad(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = ("DELETE FROM Table_WorkLoad WHERE ID = '"+id+"' ");
        Cursor cursor = db.rawQuery(query,null);
        if(cursor.getCount() > 0 ){

            return true;
        }else{
            return false;
        }
    }

    public boolean deleteDataTask(String id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * From Table_Task where Start_Date='" + id + "' ";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() > 0 ){
            String where ="Start_Date ='"+id+"' ";
            db.delete("Table_Task", where, null);
            return true;
        }else{

            return false;
        }
    }

    public boolean deleteDataAllClass(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "Select * From Table_Schedule ";

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount() > 0 ){
            db.delete("Table_Schedule", null, null);
            return true;
        }else{
            return false;
        }
    }


    public boolean deleteDataAllWork()
    {
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "Select * From Table_Workload ";

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount() > 0 ){
            db.delete("Table_Workload", null, null);
            return true;
        }else{
            return false;
        }
    }

    public boolean deleteDataAllHistory(){
        SQLiteDatabase db = this.getWritableDatabase();

        String sql = "Select * From Table_History ";

        Cursor cursor = db.rawQuery(sql,null);
        if(cursor.getCount() > 0 ){
            db.delete("Table_History", null, null);
            return true;
        }else{
            return false;
        }
    }


    public void deleteAllDataSchedWork()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Table_Workload", null, null);
    }

    public boolean deleteDataClass(String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * From Table_Schedule where Day='" + day + "' ";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() > 0 ){
            String where ="Day ='"+day+"' ";
            db.delete("Table_Schedule", where, null);
            return true;
        }else{

            return false;
        }
    }

    public boolean deleteDataWork(String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * From Table_Workload where Day='" + day + "' ";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() > 0 ){
            String where ="Day ='"+day+"' ";
            db.delete("Table_Workload", where, null);
            return true;
        }else{

            return false;
        }
    }

    public boolean deleteDataEventsMonth(String month)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * From Table_Task where Month='" + month + "' ";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() > 0 ){
            String where ="Month ='"+month+"' ";
            db.delete("Table_Task", where, null);
            return true;
        }else{

            return false;
        }
    }


    public boolean deleteDataEvents(String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "Select * From Table_Task where Start_Date ='" + day + "' ";

        Cursor cursor = db.rawQuery(sql,null);

        if(cursor.getCount() > 0 ){
            String where ="Start_Date  ='"+day+"' ";
            db.delete("Table_Task", where, null);
            return true;
        }else{

            return false;
        }
    }


    public void deleteItem(int id,String day)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where ="Id ="+id+" and Day ="+day+" ";
        db.delete("Table_Schedule", where, null);
    }
   public boolean updateClassSched(int id,String daily, String location, String start_time, String end_time,
                                   String alert,String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

       value.put("ID", id);
        value.put("Daily_Task", daily);
        value.put("Location", location);
        value.put("Start_Time", start_time);
        value.put("End_Time", end_time);
        value.put("Alert_Time", alert);
        value.put("Status", stat);

       long result = db.update("Table_Schedule",value,"ID="+id,null);

        if(result > 0){
            return true;
        }else{
            return  false;
        }

    }


    public boolean updateClassAlarm(String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("Status", stat);

        long result = db.update("Table_Schedule",value,null,null);

        if(result > 0){
            return true;
        }else{
            return  false;
        }

    }

    public boolean updateWorkAlarm(String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("Status", stat);

        long result = db.update("Table_Workload",value,null,null);

        if(result > 0){
            return true;
        }else{
            return  false;
        }

    }

    public boolean updateSemester(String semester) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();


        value.put("Semester", semester);

        long result = db.update("Table_Schedule",value,null,null);

        if(result > 0){
            return true;
        }else{
            return  false;
        }

    }

    public boolean updateWorkLoad(int id,String daily, String location, String start_time, String end_time,
                                  String alert,String stat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues value = new ContentValues();

        value.put("ID", id);
        value.put("Company", daily);
        value.put("Location", location);
        value.put("Start_Time", start_time);
        value.put("End_Time", end_time);
        value.put("Alert_Time", alert);
        value.put("Status", stat);

        long result = db.update("Table_WorkLoad",value,"ID="+id,null);

        if(result > 0){
            return true;
        }else{
            return  false;
        }

    }

    public boolean searchData(String day,String start_h, String start_f,String end_h, String end_f){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "Select * FROM Scheduller_Table Where Day ='"+day+"' and Start_Hour" +
                " ='"+start_h+"' and Format_Start ='"+start_f+"'" +
                "and End_Hour ='"+end_h+"' and Format_End ='"+end_f+"' ";
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.getCount() > 0 ){

            return true;
        }else{
            return false;
        }

    }
}
