package com.example.mondelavictoria.todaysched;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Date;
import java.util.Locale;

/**
 * Created by Rey Dela Victoria on 9/12/2018.
 */

public class NotePreviewActivity extends AppCompatActivity {

    DataBase_Helper db;
    Button buttonDelete,buttonUpdate;
    String val1;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_preview_activity);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new DataBase_Helper(this);


        buttonUpdate = (Button)findViewById(R.id.buttonUpdate);
        buttonDelete = (Button)findViewById(R.id.buttonDelete);
        //Toast.makeText(this,String.valueOf(Task_Event.mCalendarView.getSelectedDate()),Toast.LENGTH_LONG).show();

        Intent intent = getIntent();
        TextView note = (TextView) findViewById(R.id.note);
        TextView location = (TextView) findViewById(R.id.location);

        val1 = getIntent().getExtras().getString("Val");

        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Boolean del =  db.deleteDataTask(val1);
                    if(del == true){
                        Toast.makeText(NotePreviewActivity.this,"Data Deleted Successfully",Toast.LENGTH_LONG).show();
                    }else{
                        //Toast.makeText(NotePreviewActivity.this," Failed",Toast.LENGTH_LONG).show();
                        Toast.makeText(NotePreviewActivity.this,"No events found on"+" "+val1,Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
            }
        });

        //Toast.makeText(this,val1,Toast.LENGTH_LONG).show();
        // get all data from sqlite
        Cursor cursor = db.getDataClass("Select * From Table_Task WHERE Start_Date = '"+val1+"'");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String what = cursor.getString(0);
                String subject = cursor.getString(1);
                String location_ = cursor.getString(2);
                String starttime = cursor.getString(3);

                note.setText(what);
                location.setText(subject);
                //date_.setText(String.valueOf(getFormattedDate(myEventDay.getCalendar().getTime())));


               //Toast.makeText(this,what+subject+location_+starttime,Toast.LENGTH_LONG).show();

            }
        }


        if (intent != null) {
            Object event = intent.getParcelableExtra(Task_Event.EVENT);
            if(event instanceof MyEventDay){
                MyEventDay myEventDay = (MyEventDay)event;
                getSupportActionBar().setTitle(getFormattedDate(myEventDay.getCalendar().getTime()));
                //date_.setText(String.valueOf( myEventDay.getCalendar()));
                //note.setText(myEventDay.getNote());
                //location.setText(myEventDay.getLocation());

                return;
            }
            if(event instanceof EventDay){
                EventDay eventDay = (EventDay)event;
                getSupportActionBar().setTitle(getFormattedDate(eventDay.getCalendar().getTime()));
            }
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String getFormattedDate(Date date) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return simpleDateFormat.format(date);
    }
}
