package com.example.mondelavictoria.todaysched;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;
import com.applandeo.materialcalendarview.listeners.OnDayClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.mondelavictoria.todaysched.NotePreviewActivity.getFormattedDate;

public class Task_Event extends AppCompatActivity {

    public static final String RESULT = "result";
    public static final String EVENT = "event";
    private static final int ADD_NOTE = 44;
    static CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    DataBase_Helper db;
    MyEventDay myEventDay1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task__event);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new DataBase_Helper(this);

        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);

        // get all data from sqlite
        Cursor cursor = db.getDataClass("Select * From Table_Task");
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String task = cursor.getString(0);
                String location_ = cursor.getString(1);
                String when = cursor.getString(2);
                String starttime = cursor.getString(3);
                String month = cursor.getString(4);

                //Toast.makeText(this,when,Toast.LENGTH_LONG).show();

                SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
                try {

                    Date date = format.parse(when);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);

                    //int c = cal.get(Calendar.DAY_OF_YEAR);
                    cal.getTime();

                    // Toast.makeText(this,String.valueOf(cal.getTime()), Toast.LENGTH_LONG).show();

                    myEventDay1 = new MyEventDay(cal,R.drawable.today, task,location_,month);

                    mEventDays.add(myEventDay1);
                    mCalendarView.setEvents(mEventDays);



                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }
        }

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNote();
            }
        });
        mCalendarView.setOnDayClickListener(new OnDayClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDayClick(EventDay eventDay) {
                previewNote(eventDay);


            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ADD_NOTE && resultCode == RESULT_OK) {

           /* // get all data from sqlite
            Cursor cursor = db.getDataClass("Select * From Table_Task");
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String task = cursor.getString(0);
                    String location_ = cursor.getString(1);
                    String when = cursor.getString(2);
                    String starttime = cursor.getString(3);
                    Toast.makeText(this,task+when+location_+starttime,Toast.LENGTH_LONG).show();

                }
            }*/

            MyEventDay myEventDay = data.getParcelableExtra(RESULT);
            mCalendarView.setDate(myEventDay.getCalendar());
            mEventDays.add(myEventDay);
            mCalendarView.setEvents(mEventDays);

        }
    }
    private void addNote() {
        Intent intent = new Intent(this, AddNoteActivity.class);
        startActivityForResult(intent, ADD_NOTE);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    private void previewNote(EventDay eventDay) {



        // get all data from sqlite
        Cursor cursor = db.getDataClass("Select * From Table_Task");

        Intent intent = new Intent(this, NotePreviewActivity.class);
        if(eventDay instanceof MyEventDay){
            //intent.putExtra(EVENT, (MyEventDay) eventDay);
        }
        intent.putExtra("Val",String.valueOf(getFormattedDate(mCalendarView.getSelectedDate().getTime())));
        //getFormattedDate(eventDay.getCalendar().getTime());
        //Toast.makeText(this,String.valueOf(getFormattedDate(mCalendarView.getSelectedDate().getTime())), Toast.LENGTH_LONG).show();
        startActivity(intent);
    }
}