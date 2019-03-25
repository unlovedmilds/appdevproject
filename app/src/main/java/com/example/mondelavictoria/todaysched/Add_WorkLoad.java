package com.example.mondelavictoria.todaysched;

import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.r0adkll.slidr.model.SlidrInterface;

public class Add_WorkLoad extends AppCompatActivity {

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
    String start_,endh;
    private Switch aSwitch;
    int sth,eth;
    int _h1;
    public int _h2;
    public int h1;
    public int h2;
    int startTimeFormat=0;
    int endTimeFormat=0;
    int startmin=0,endmin=0;
    int x=0,i=0;
    boolean res = false;
    int etf,stf;
    TextInputEditText textInputEditTextCompany,textInputEditTextLocation,textInputEditTextStarttime,textInputEditTextEndTime;

    DataBase_Helper db,db1;

    private SlidrInterface slidr;

    TextView endTime,date;
    Button buttonSave;
    String days;
    String valSwitch = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__work_load);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new DataBase_Helper(this);

        days = getIntent().getExtras().getString("Val");

        aSwitch = (Switch)findViewById(R.id.switch1);

        spinner = (Spinner) findViewById(R.id.spinner);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerValue);
        spinner.setAdapter(adapter);


        //Toast.makeText(Add_Class_Sched.this,spinnerAlert,Toast.LENGTH_LONG).show();

        textInputEditTextCompany = (TextInputEditText)findViewById(R.id.textInputEditTextCompany);
        textInputEditTextLocation = (TextInputEditText)findViewById(R.id.textInputEditTextLocation);
        textInputEditTextStarttime = (TextInputEditText)findViewById(R.id.textInputEditTextStartTime);
        textInputEditTextEndTime = (TextInputEditText)findViewById(R.id.textInputEditTextEndTime);
        buttonSave = (Button)findViewById(R.id.buttonSave);




        textInputEditTextStarttime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);



                timepickerDialog = new TimePickerDialog(Add_WorkLoad.this,
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
                                //textInputEditTextStarttime.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);
                                textInputEditTextStarttime.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);
                                hour = String.valueOf(hourOfDay).trim();
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


                timepickerDialog = new TimePickerDialog(Add_WorkLoad.this,
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
                                _h2 = hourOfDay;
                                endTimeFormat = endTimeFormat + hourOfDay + 12;
                                textInputEditTextEndTime.setText( hourOfDay+":"+String.format("%02d" ,minute)+" "+format);
                                _hour = String.valueOf(hourOfDay).trim();
                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(aSwitch.isChecked()){
                    switchOn = aSwitch.getTextOn().toString();
                    valSwitch = switchOn;
                }else{
                    switchOff = aSwitch.getTextOff().toString();
                    valSwitch = switchOff;
                }

                alerts = spinner.getSelectedItem().toString();

                Cursor cursor1 = db.getData("SELECT *  From Table_WorkLoad");
                if (cursor1.moveToFirst())

                {


                    int _id = cursor1.getInt(0);
                    String _daily = cursor1.getString(1);
                    String _location = cursor1.getString(2);
                    String _start = cursor1.getString(3);
                    String _end = cursor1.getString(4);
                    _date = cursor1.getString(5);
                    _alert = cursor1.getString(6);
                    start_ = cursor1.getString(7);
                    _f = cursor1.getString(8);
                    endh = cursor1.getString(9);
                    _f_ = cursor1.getString(10);
                    sth = cursor1.getInt(11);
                    eth = cursor1.getInt(12);
                    String stat = cursor1.getString(13);



                }

                Cursor cursor = new DataBase_Helper(getApplicationContext()).getData("Select * From  Table_WorkLoad ");

                while (cursor.moveToNext())

                {

                    int _id = cursor.getInt(0);
                    String _daily = cursor.getString(1);
                    String _location = cursor.getString(2);
                    String _start = cursor.getString(3);
                    String _end = cursor.getString(4);
                    _date = cursor.getString(5);
                    _alert = cursor.getString(6);
                    start_h = cursor.getString(7);
                    _f = cursor.getString(8);
                    end_h = cursor.getString(9);
                    _f_ = cursor.getString(10);
                    stf = cursor.getInt(11);
                    etf = cursor.getInt(12);
                    String stat = cursor.getString(13);

                    h2 = Integer.parseInt(end_h);
                    h1 = Integer.parseInt(start_h);


                    if (days.equals(_date) && _h1 < h1 && _h1 > _h2
                            && formats.equals(_f) && format.equals(_f_)
                            ) {
                        Toast.makeText(getApplication(), "error", Toast.LENGTH_SHORT).show();
                        x++;
                        i--;
                    } else if (days.equals(_date) && _h1 < h2 && _h1 > Integer.valueOf(start_)
                            && formats.equals(_f) && format.equals(_f_)
                            ) {
                        Toast.makeText(getApplication(), "erro", Toast.LENGTH_SHORT).show();
                        x++;
                        i--;
                    } else if (days.equals(_date) && _h1 < h2 && _h2 > Integer.valueOf(start_)
                            && formats.equals(_f) && format.equals(_f_)
                            ) {
                        Toast.makeText(getApplication(), "err", Toast.LENGTH_SHORT).show();
                        x++;
                        i--;
                    }

                }
                    if (days.equals(_date)  && _h1 == 12 && _h2 > h1
                            && formats.equals(_f) && format.equals(_f_)
                            ) {
                        Toast.makeText(getApplication(), "errors", Toast.LENGTH_SHORT).show();
                        x++;
                        i--;
                    }




                if(x == i) {
                    try {
                        Boolean result = db.insertWorkLoad(textInputEditTextCompany.getText().toString(),
                                textInputEditTextLocation.getText().toString(),
                                textInputEditTextStarttime.getText().toString().trim(),
                                textInputEditTextEndTime.getText().toString().trim(),
                                days, alerts, hour, formats, _hour, format, startmin, endmin, valSwitch);


                        if (result == true) {

                            Toast.makeText(getApplication(), "Faild", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(getApplication(), "Save", Toast.LENGTH_SHORT).show();
                            finish();

                        }

                    } catch (Exception e) {
                        System.out.print(e.getMessage());
                    }

                }

            }
        });
    }

}
