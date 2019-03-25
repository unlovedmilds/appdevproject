package com.example.mondelavictoria.todaysched;


import android.app.Activity;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.applandeo.materialcalendarview.CalendarView;

import java.util.Calendar;

/**
 * Created by Mon Dela Victoria on 9/12/2018.
 */

public class AddNoteActivity extends AppCompatActivity {

    CalendarView datePicker;
    String newTime;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_note_activity);
       final  DataBase_Helper db = new DataBase_Helper(this);

        datePicker = (CalendarView) findViewById(R.id.datePicker);
        Button button = (Button) findViewById(R.id.addNoteButton);
        final EditText noteEditText = (EditText) findViewById(R.id.noteEditText);
        final EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
        datePicker.getSelectedDate();


        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Intent returnIntent = new Intent();

                //ako gikoha ang ang selected month
                SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
                Calendar cal = Calendar.getInstance();
                cal.setTime(datePicker.getSelectedDate().getTime());
                cal.add(Calendar.MONTH,0);
                newTime = sdf.format(cal.getTime());

                //Toast.makeText(getApplication(),newTime,Toast.LENGTH_LONG).show();

                MyEventDay myEventDay = new MyEventDay(datePicker.getSelectedDate(),
                        R.drawable.today, noteEditText.getText().toString(),locationEditText.getText().toString(),newTime);
                returnIntent.putExtra(Task_Event.RESULT, myEventDay);


                setResult(Activity.RESULT_OK, returnIntent);
                finish();
                //myEventDay.setNote(noteEditText.getText().toString());
                //myEventDay.setLocation(locationEditText.getText().toString());

                Boolean result = db.addDailyTask(myEventDay);
                if(result == true){

                    Toast.makeText(getApplication(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getApplication(),"Data Insertion Failed",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}