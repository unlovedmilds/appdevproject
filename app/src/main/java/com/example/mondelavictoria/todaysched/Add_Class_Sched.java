package com.example.mondelavictoria.todaysched;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.r0adkll.slidr.model.SlidrInterface;

/**
 * Created by Rey Dela Victoria on 7/24/2018.
 */

public class Add_Class_Sched extends AppCompatActivity {


    String[] spinnerValue = {"0", "10", "30", "60"};
    SpinnerAdapter adapter;

    TimePickerDialog timepickerDialog;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    String formats,format;
    Spinner spinner,spinnerDate;
    String _dates;
    String starttime;
    String endtime;
    String hour,_hour;
    String start_h;
    String end_h;
    String _alert;
    String _f,_f_;
    String alerts;
    String _date;
    String switchOn,switchOff;
    private Switch aSwitch;
    int _h1;
    public int _h2;
    public int h1;
    public int h2;
    int startTimeFormat=0;
    int endTimeFormat=0;
    int x=0,i=0;
    boolean res = false;
    int etf,stf;
    TextInputEditText textInputEditTextSubject,textInputEditTextLocation,textInputEditTextStarttime,textInputEditTextEndTime;

    DataBase_Helper db,db1;
    Daily_Task_Constructor dts;

    private SlidrInterface slidr;
    AutoCompleteTextView autoCompleteTextViewDailyTask,autoCompleteTextViewLocation,
            autoCompleteTextViewStartTime,autoCompleteTextViewEndTime,autoCompleteTextViewDate;
    TextView endTime,date;
    EditText startTime;
    FloatingActionButton floatingActionButton;
    TextView textViewAddDailyTask;
    Button buttonSave;
    String days;
    String valSwitch = "";
    String semester_year;
    String semester = "1st Semester";
    String endh;
    String start_;
    String w_f;
    String start;
    int sth_work,eth_work;
    int startmin=0,endmin=0;
    boolean oke = false;
    boolean ok = false;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_class_sched);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String dateString = sdf.format(date);
        int dateString2 = Integer.parseInt(dateString);
        int dateStringVal = dateString2+1;
        semester_year = String.valueOf( dateString2+"-"+dateStringVal);

        db = new DataBase_Helper(this);

        days = getIntent().getExtras().getString("Val");

        aSwitch = (Switch)findViewById(R.id.switch1);

        spinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValue);
        spinner.setAdapter(adapter);


        //Toast.makeText(Add_Class_Sched.this,spinnerAlert,Toast.LENGTH_LONG).show();

        textInputEditTextSubject = (TextInputEditText)findViewById(R.id.textInputEditTextSubject);
        textInputEditTextLocation = (TextInputEditText)findViewById(R.id.textInputEditTextLocation);
        textInputEditTextStarttime = (TextInputEditText)findViewById(R.id.textInputEditTextStartTime);
        textInputEditTextEndTime = (TextInputEditText)findViewById(R.id.textInputEditTextEndTime);
        buttonSave = (Button)findViewById(R.id.buttonSave);


        Cursor cursor1 = db.getData("SELECT *  From Table_WorkLoad");
        if (cursor1.moveToNext())

        {


            int _ids = cursor1.getInt(0);
            String _dailys = cursor1.getString(1);
            String _locations = cursor1.getString(2);
            String _starts = cursor1.getString(3);
            String _ends = cursor1.getString(4);
            _date = cursor1.getString(5);
            _alert = cursor1.getString(6);
            start_ = cursor1.getString(7);
            w_f = cursor1.getString(8);
            endh = cursor1.getString(9);
            _f_ = cursor1.getString(10);
            sth_work = cursor1.getInt(11);
            eth_work = cursor1.getInt(12);
            String stats = cursor1.getString(13);

        }
        //Toast.makeText(getApplication(), String.valueOf(sth_work)+w_f, Toast.LENGTH_SHORT).show();


        textInputEditTextStarttime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);




                timepickerDialog = new TimePickerDialog(Add_Class_Sched.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    formats = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    formats = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;
                                    formats = "PM";

                                }else {

                                    formats = "AM";
                                }


                                //textViewTime.setText(hourOfDay + ":" + minute +" "+ format);
                                //startTime.setText(String.format("%02d:%02d", hourOfDay ,minute)+formats);

                                startmin = Integer.parseInt(hourOfDay+""+minute);
                                _h1 = hourOfDay;
                                startTimeFormat = startTimeFormat + hourOfDay + 12;
                                textInputEditTextStarttime.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);
                                start = textInputEditTextStarttime.getText().toString();
                                //textInputEditTextStarttime.setText( hourOfDay+":"+String.format("%02d" ,minute)+" " +formats);
                                hour = String.valueOf(hourOfDay).trim();

                                Cursor cursor = db.getData("Select * From  Table_Schedule where Day = '"+days+"' ");

                                while (cursor.moveToNext() &&  !oke)

                                {

                                    int _id = cursor.getInt(0);
                                    String _daily = cursor.getString(1);
                                    String _location = cursor.getString(2);
                                    String _start = cursor.getString(3);
                                    String _end = cursor.getString(4);
                                    String _date = cursor.getString(5);
                                    String _alert = cursor.getString(6);
                                    String start_h = cursor.getString(7);
                                    String _f = cursor.getString(8);
                                    String end_h = cursor.getString(9);
                                    String _f_ = cursor.getString(10);
                                    int stf = cursor.getInt(11);
                                    int etf = cursor.getInt(12);
                                    String stat = cursor.getString(13);
                                    String semesteryear = cursor.getString(14);
                                    String semester = cursor.getString(15);


                                    //Toast.makeText(Add_Class_Sched.this,String.valueOf(etf),Toast.LENGTH_LONG).show();
                                    //int s = Integer.valueOf(textInputEditTextStarttime.getText().toString());
                                    if (startmin < etf  && startmin > stf
                                            && _f.equals(formats)) {
                                        textInputEditTextStarttime.setError("Time Comflicted");
                                        oke = true;
                                        ok = false;

                                    } else{
                                        ok = true;
                                    }

                                }




                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();
            }



        });


        textInputEditTextEndTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);


                timepickerDialog = new TimePickerDialog(Add_Class_Sched.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                if (hourOfDay == 0) {

                                    hourOfDay += 12;

                                    format = "AM";
                                }
                                else if (hourOfDay == 12) {

                                    format = "PM";

                                }
                                else if (hourOfDay > 12) {

                                    hourOfDay -= 12;
                                    format = "PM";

                                }else {

                                    format = "AM";
                                }


                                //textViewTime.setText(hourOfDay + ":" + minute +" "+ format);
                                endmin = Integer.parseInt(hourOfDay+""+minute);
                               // Toast.makeText(getApplication(), String.valueOf(startmin) + String.valueOf(endmin) , Toast.LENGTH_SHORT).show();

                                _h2 = hourOfDay;
                                endTimeFormat = endTimeFormat + hourOfDay + 12;
                                textInputEditTextEndTime.setText( hourOfDay+":"+String.format("%02d" ,minute)+" "+format);
                                _hour = String.valueOf(hourOfDay).trim();


                                Cursor cursor = db.getData("Select * From  Table_Schedule where Day = '"+days+"' ");

                                while (cursor.moveToNext() &&  !oke)

                                {

                                    int _id = cursor.getInt(0);
                                    String _daily = cursor.getString(1);
                                    String _location = cursor.getString(2);
                                    String _start = cursor.getString(3);
                                    String _end = cursor.getString(4);
                                    String _date = cursor.getString(5);
                                    String _alert = cursor.getString(6);
                                    String start_h = cursor.getString(7);
                                    String _f = cursor.getString(8);
                                    String end_h = cursor.getString(9);
                                    String _f_ = cursor.getString(10);
                                    int stf = cursor.getInt(11);
                                    int etf = cursor.getInt(12);
                                    String stat = cursor.getString(13);
                                    String semesteryear = cursor.getString(14);
                                    String semester = cursor.getString(15);


                                    //Toast.makeText(Add_Class_Sched.this,String.valueOf(etf),Toast.LENGTH_LONG).show();
                                    //int s = Integer.valueOf(textInputEditTextStarttime.getText().toString());
                                    if (endmin > stf  && endmin > etf
                                            ) {
                                        textInputEditTextEndTime.setError("Time Comflicted");
                                        oke = true;
                                        ok = false;
                                        buttonSave.setEnabled(false);

                                    }else  if (endmin > stf  && endmin < etf
                                            ) {
                                        textInputEditTextEndTime.setError("Time Comflicted");
                                        oke = true;
                                        ok = false;
                                        buttonSave.setEnabled(false);

                                    }else{
                                        ok = true;

                                    }

                                }


                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val1 = textInputEditTextSubject.getText().toString();
                String val2 = textInputEditTextLocation.getText().toString();
                String val3 = textInputEditTextStarttime.getText().toString();
                String val4 = textInputEditTextEndTime.getText().toString();

                if(val1.isEmpty() || val2.isEmpty() || val3.isEmpty() || val4.isEmpty()){

                    Toast.makeText(getApplication(), "Pls fill all entries field" , Toast.LENGTH_SHORT).show();

                }else {

                    if (aSwitch.isChecked()) {
                        switchOn = aSwitch.getTextOn().toString();
                        valSwitch = switchOn;
                    } else {
                        switchOff = aSwitch.getTextOff().toString();
                        valSwitch = switchOff;
                    }

                    alerts = spinner.getSelectedItem().toString();


                    if (startmin != 0) {
                        try {
                            Boolean result = db.insert(textInputEditTextSubject.getText().toString(),
                                    textInputEditTextLocation.getText().toString(),
                                    textInputEditTextStarttime.getText().toString().trim(),
                                    textInputEditTextEndTime.getText().toString().trim(),
                                    days, alerts, hour, formats, _hour, format, startmin, endmin, valSwitch, semester_year, semester);


                            if (result == true) {

                                // Toast.makeText(getApplication(), "Conflicts", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplication(), "Saves", Toast.LENGTH_SHORT).show();

                                Intent myIntent = new Intent(Add_Class_Sched.this, MainActivity.class);



                                /*ActivityOptions options =
                                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.transition, R.anim.transition);
                                startActivity(myIntent, options.toBundle());*/
                                finish();
                            }

                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }



                    if (ok) {
                        try {
                            Boolean result = db.insert(textInputEditTextSubject.getText().toString(),
                                    textInputEditTextLocation.getText().toString(),
                                    textInputEditTextStarttime.getText().toString().trim(),
                                    textInputEditTextEndTime.getText().toString().trim(),
                                    days, alerts, hour, formats, _hour, format, startmin, endmin, valSwitch, semester_year, semester);


                            if (result == true) {

                                // Toast.makeText(getApplication(), "Conflicts", Toast.LENGTH_SHORT).show();
                            } else {

                                Toast.makeText(getApplication(), "Save", Toast.LENGTH_SHORT).show();

                                Intent myIntent = new Intent(Add_Class_Sched.this, MainActivity.class);



                                /*ActivityOptions options =
                                        ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.transition, R.anim.transition);
                                startActivity(myIntent, options.toBundle());*/
                                finish();
                            }

                        } catch (Exception e) {
                            System.out.print(e.getMessage());
                        }
                    }

                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    /* public void add(){

        try {
            Boolean result = db.insert(textInputEditTextSubject.getText().toString(),
                    textInputEditTextLocation.getText().toString(),
                    textInputEditTextStarttime.getText().toString().trim(),
                    textInputEditTextEndTime.getText().toString().trim(),
                    days, alerts, hour, formats, _hour, format, startmin, endmin, valSwitch);


            if (result == true) {

                // Toast.makeText(getApplication(), "Conflicts", Toast.LENGTH_SHORT).show();
            } else {


                Toast.makeText(getApplication(), "Save", Toast.LENGTH_SHORT).show();
                startmin = 0;

            }

        } catch (Exception e) {
            System.out.print(e.getMessage());
        }
    }*/
}
