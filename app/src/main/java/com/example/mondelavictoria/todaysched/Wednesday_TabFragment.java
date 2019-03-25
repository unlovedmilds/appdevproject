package com.example.mondelavictoria.todaysched;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class Wednesday_TabFragment extends Fragment {

    ImageButton button_add_event;
    FloatingActionButton floatingActionButton,floatingActionButton1;
    private BottomSheetBehavior mBottomSheetBehavior;
    private TextView mTextViewState;
    AppCompatActivity activity;
    TextView ttime;

    DataBase_Helper db;
    ListAdapter adapter = null;
    ListView listViewFriday;
    ArrayList<ClassSched_Diagram> list;

    ListAdapter1 adapter1 = null;
    ListView listViewWorkLoad;
    ArrayList<WorkSched_Diagram> list1;

    String FileName = "file";
    String strName,name;
    String day;
    String start_,end_time_,subject_,location_,stat_;
    String starttime_work,endtime_work,company_work,locationwork;
    String starttime_his,endtime_his,subject_his,location_his,year_his,semester_his;

    String formats,format;

    String _day_ = "Wednesday";
    String semester;

    TextView tdate,textViewDate_;
    TextView textViewSem,textView;

    public Wednesday_TabFragment(){}

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.activity_wednesday__tab_fragment, container, false);

        db = new DataBase_Helper(getContext());
        Daily_Task_Constructor constructor = new Daily_Task_Constructor();

        ttime = (TextView )v.findViewById(R.id.textView6);
        textViewDate_ = (TextView)v.findViewById(R.id.textViewDate_);
        textViewSem = (TextView)v.findViewById(R.id.textViewSem);


        textViewSem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(getContext(),textViewSem);
                popupMenu.getMenuInflater().inflate(R.menu.menu_pop,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        String sem = String.valueOf(item.getTitle());
                        Boolean res = db.updateSemester(sem);

                        if(res == true){
                            update();
                            // Toast.makeText(getContext(),"Data Updated Successfully",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getContext()," Failed",Toast.LENGTH_LONG).show();
                        }


                        return true;
                    }
                });
                popupMenu.show();
            }
        });


        long date_ = System.currentTimeMillis();
        SimpleDateFormat sdf_ = new SimpleDateFormat("MMM d yyyy");
        String dateString_ = sdf_.format(date_);
        textViewDate_.setText(dateString_);

        listViewFriday = (ListView) v.findViewById(R.id.listViewFri);
        listViewWorkLoad = (ListView) v.findViewById(R.id.listView1);
        list = new ArrayList<>();
        list1 = new ArrayList<>();


        TextView textView6 = (TextView)v.findViewById(R.id.textView6);
        textView = (TextView)v.findViewById(R.id.textView);
        TextView textView2 = (TextView)v.findViewById(R.id.textView2);
        tdate = (TextView)v.findViewById(R.id.textViewDate);
        tdate.setSelected(true);
        tdate.setSingleLine();


        Cursor cursor = db.getDataClass("Select * From Table_Schedule where Day = '"+_day_+"' ");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id  =  cursor.getInt(0);
                String subject = cursor.getString(1);
                String location = cursor.getString(2);
                String starttime = cursor.getString(3);
                String endtime = cursor.getString(4);
                day = cursor.getString(5);
                String alerttime = cursor.getString(6);
                String starthour = cursor.getString(7);
                String formatstart = cursor.getString(8);
                String endhour = cursor.getString(9);
                String formatend = cursor.getString(10);
                int starttimeformat = cursor.getInt(11);
                int endtimeformat = cursor.getInt(12);
                String status = cursor.getString(13);
                String semesteryear = cursor.getString(14);
                semester = cursor.getString(15);

                //Toast.makeText(getContext(), day, Toast.LENGTH_SHORT).show();

                list.add(new ClassSched_Diagram(id,subject, location, starttime, endtime,
                        day,alerttime,starthour,formatstart,endhour,formatend,starttimeformat,endtimeformat,status,semesteryear,semester ));
                textView.setText("Your Class Schedule");
                textViewSem.setText(semester);

            }
        }else{
            textViewSem.setText(semester);
            textView.setText("No Class Schedule Added");

        }
        adapter = new ListAdapter(getContext(), R.layout.class_sched_listview, list);
        listViewFriday.setAdapter(adapter);
        adapter.notifyDataSetChanged();





        listViewFriday.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(getContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());


                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update
                           /* Cursor c = db.getData("SELECT ID FROM Table_Schedule");
                            ArrayList<Integer> arrID = new ArrayList<Integer>();
                            while (c.moveToNext()) {
                                arrID.add(c.getInt(0));
                            }*/
                            // show dialog update at here
                            int _id = ((ClassSched_Diagram) adapter.getItem(position)).getId();
                            start_ = ((ClassSched_Diagram) adapter.getItem(position)).getStartTime();
                            end_time_ = ((ClassSched_Diagram) adapter.getItem(position)).getEndTime();
                            subject_ = ((ClassSched_Diagram) adapter.getItem(position)).getSubject();
                            location_ = ((ClassSched_Diagram) adapter.getItem(position)).getLocation();
                            stat_ = ((ClassSched_Diagram) adapter.getItem(position)).getStatus();

                            showDialogUpdate(getContext(), _id);

                        } else {
                            // delete
                            int _id = ((ClassSched_Diagram) adapter.getItem(position)).getId();
                            starttime_his = ((ClassSched_Diagram) adapter.getItem(position)).getStartTime();
                            endtime_his = ((ClassSched_Diagram) adapter.getItem(position)).getEndTime();
                            subject_his = ((ClassSched_Diagram) adapter.getItem(position)).getSubject();
                            location_his = ((ClassSched_Diagram) adapter.getItem(position)).getLocation();
                            year_his = ((ClassSched_Diagram) adapter.getItem(position)).getYear();
                            semester_his = ((ClassSched_Diagram) adapter.getItem(position)).getSemester();
                            showDialogDelete(_id);

                            /*list.remove(position);
                            adapter = new ListAdapter(getContext(), R.layout.class_sched_listview, list);
                            listViewFriday.setAdapter(adapter);
                            adapter.notifyDataSetChanged();*/
                        }
                    }
                });
                dialog.show();
                return true;

            }

        });


        listViewWorkLoad.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                //Toast.makeText(getContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                CharSequence[] items = {"Update", "Delete"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());


                dialog.setTitle("Choose an action");
                dialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (item == 0) {
                            // update

                            // show dialog update at here
                            int _id = ((WorkSched_Diagram) adapter1.getItem(position)).getId();
                            starttime_work = ((WorkSched_Diagram) adapter1.getItem(position)).getStartTime();
                            endtime_work = ((WorkSched_Diagram) adapter1.getItem(position)).getEndTime();
                            company_work = ((WorkSched_Diagram) adapter1.getItem(position)).getCompany();
                            locationwork = ((WorkSched_Diagram) adapter1.getItem(position)).getLocation();

                            showDialogUpdateWorkLoad(getContext(), _id);

                        } else {
                            // delete
                            int _id = ((WorkSched_Diagram) adapter1.getItem(position)).getId();

                            showDialogDeleteWorkLoad(_id);

                        }
                    }
                });
                dialog.show();
                return true;

            }

        });



        final View bottomSheet = v.findViewById(R.id.bottom_sheet);

        button_add_event = (ImageButton) v.findViewById(R.id.button_add_event);
        floatingActionButton = (FloatingActionButton)v.findViewById(R.id.floating);
        floatingActionButton1 = (FloatingActionButton)v.findViewById(R.id.floating1);
        activity = new AppCompatActivity();
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);


        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
        String dateString = sdf.format(date);
        int dateString2 = Integer.parseInt(dateString);
        int dateStringVal = dateString2+1;
        tdate.setText(dateString+"-"+String.valueOf(dateStringVal));

        // ttime = (TextView) v.findViewById(R.id.textViewTime);
        long date1 = System.currentTimeMillis();
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
        String dateString1 = sdf1.format(date1);
        //ttime.setText(dateString1);

                                /*  TextView tdate = (TextView) v.findViewById(R.id.textViewDate);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy");
                                String dateString = sdf.format(date);
                                tdate.setText(dateString);

                                ttime = (TextView) v.findViewById(R.id.textViewTime);
                                long date1 = System.currentTimeMillis();
                                SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm a");
                                String dateString1 = sdf1.format(date1);

                                Date d = null;
                                try {
                                    d = sdf1.parse(dateString1);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Calendar cal = Calendar.getInstance();
                                cal.setTime(d);
                                cal.add(Calendar.MINUTE, -10);
                                String newTime = sdf1.format(cal.getTime());
                                ttime.setText(newTime);
*/




        button_add_event.setOnClickListener(new View.OnClickListener() {
            boolean bottomexpand = false;

            @Override
            public void onClick(View v) {
                //String v1 = ttime.getText().toString();
                //Toast.makeText(getContext(),v1,Toast.LENGTH_LONG).show();
                if(bottomexpand == false) {
                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                    button_add_event.setImageResource(R.drawable.ic_expand_less_downarrow_24dp);
                    // Intent addItem=new Intent(getContext(),MainActivity.class);
                    // startActivity(addItem);
                    bottomexpand=true;

                }else if(bottomexpand){

                    mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    button_add_event.setImageResource(R.drawable.ic_expand_less_uparrow_24dp);
                    bottomexpand = false;

                }
            }
        });
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sunday = "Wednesday";
                Intent intent = new Intent(getContext(),Add_WorkLoad.class);
                intent.putExtra("Val",sunday);
                startActivity(intent);
                //Toast.makeText(getContext(),"Ok",Toast.LENGTH_LONG).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sunday  = "Wednesday";
                Intent intent = new Intent(getContext(),Add_Class_Sched.class);
                intent.putExtra("Val",sunday );
                startActivity(intent);
                //Toast.makeText(getContext(),"Ok",Toast.LENGTH_LONG).show();
            }
        });


        mBottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            boolean bottomexpand1 = false;
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        //mTextViewState.setText("Collapsed");
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        while(true) {
                            if (bottomexpand1 == false) {
                                mBottomSheetBehavior.setState(BottomSheetBehavior.STATE_DRAGGING);
                                bottomexpand1 = true;
                                button_add_event.setImageResource(R.drawable.ic_expand_less_downarrow_24dp);
                            } else {
                                if (bottomexpand1) {
                                    button_add_event.setImageResource(R.drawable.ic_expand_less_uparrow_24dp);
                                    bottomexpand1 = false;
                                }
                            }
                            break;
                        }

                    case BottomSheetBehavior.STATE_EXPANDED:
                        //mTextViewState.setText("Expanded");
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        // mTextViewState.setText("Hidden");
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        //mTextViewState.setText("Settling...");
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                //mTextViewState.setText("Sliding...");
            }
        });

        /*chooseTime = v.findViewById(R.id.etChooseTime);

        chooseTime.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                calendar = Calendar.getInstance();
                currentHour = calendar.get(Calendar.HOUR_OF_DAY);
                currentMinute = calendar.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minutes) {
                        if (hourOfDay >= 12) {
                            amPm = "PM";
                        } else {
                            amPm = "AM";
                        }
                        chooseTime.setText(String.format("%02d:%02d", hourOfDay, minutes) + amPm);
                    }
                }, currentHour, currentMinute, false);

                timePickerDialog.show();
            }
        });*/


        return  v;

    }

    private void showDialogUpdate(Context context, final int position){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_update_sched);
        dialog.setTitle("Update");

        String[] spinnerValue = {"0", "10", "30", "60"};
        final TextInputEditText start_time = dialog.findViewById(R.id.textInputEditTextStartTime);
        final TextInputEditText end_time = dialog.findViewById(R.id.textInputEditTextEndTime);
        final TextInputEditText subject = dialog.findViewById(R.id.textInputEditTextSubject);
        final TextInputEditText location = dialog.findViewById(R.id.textInputEditTextLocation);
        final Spinner spinner = dialog.findViewById(R.id.spinner);
        final Switch aSwitch = (Switch)dialog.findViewById(R.id.switch1);

        start_time.setText(start_);
        end_time.setText(end_time_);
        subject.setText(subject_);
        location.setText(location_);

        aSwitch.setText(stat_);
        String sw = aSwitch.getText().toString();
        if(sw.equals("On")){
            aSwitch.setChecked(true);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerValue);
        spinner.setAdapter(adapter);

        Button btnUpdate = dialog.findViewById(R.id.buttonSave);

        // set width for dialog
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();


        start_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                int CalendarHour, CalendarMinute;
                TimePickerDialog timepickerDialog;

                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);



                timepickerDialog = new TimePickerDialog(getContext(),
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

                                start_time.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();


            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                int CalendarHour, CalendarMinute;
                TimePickerDialog timepickerDialog;

                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);



                timepickerDialog = new TimePickerDialog(getContext(),
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

                                end_time.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alerts;
                String switchOn,switchOff;
                String valSwitch = "";

                if(aSwitch.isChecked()){
                    switchOn = aSwitch.getTextOn().toString();
                    valSwitch = switchOn;
                }else{
                    switchOff = aSwitch.getTextOff().toString();
                    valSwitch = switchOff;
                }

                alerts = spinner.getSelectedItem().toString();

                Boolean res = db.updateClassSched(position,
                        subject.getText().toString().trim(),
                        location.getText().toString().trim(),
                        start_time.getText().toString().trim(),
                        end_time.getText().toString().trim(),
                        alerts, valSwitch
                );


                if(res == true){
                    Toast.makeText(getContext(),"Data Updated Successfully",Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(getContext()," Failed",Toast.LENGTH_LONG).show();
                }
                update();
                dialog.dismiss();
                //Toast.makeText(getContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();



            }
        });

    }


    private void showDialogDelete(final int id){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    Boolean del =  db.deleteData(id);
                    if(del == true){
                        Toast.makeText(getContext(),"Data Deleted Successfully",Toast.LENGTH_LONG).show();
                    }else{
                        //Toast.makeText(getContext()," Failed",Toast.LENGTH_LONG).show();
                    }

                    ClassHistory_Diagram clas = new ClassHistory_Diagram(id,subject_his,location_his,starttime_his,endtime_his,"","","",
                            "","","",0,0,"",year_his,semester_his);
                    Boolean result = db.addHistory(clas);
                    if(result == true){

                        //Toast.makeText(getContext(),"Data Inserted Successfully",Toast.LENGTH_LONG).show();
                    }else{
                        // Toast.makeText(getContext(),"Data Insertion Failed",Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                update();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogDeleteWorkLoad(final int id_){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(getContext());

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    Boolean del =  db.deleteWorkLoad(id_);
                    if(del == true){
                        Toast.makeText(getContext(),"Data Deleted Successfully",Toast.LENGTH_LONG).show();
                    }else{
                        //Toast.makeText(getContext()," Failed",Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateWorkLoad();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void showDialogUpdateWorkLoad(Context context, final int position){

        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.add_update_workload);
        dialog.setTitle("Update");

        String[] spinnerValue = {"0", "10", "30", "60"};
        final TextInputEditText start_time = dialog.findViewById(R.id.textInputEditTextStartTime);
        final TextInputEditText end_time = dialog.findViewById(R.id.textInputEditTextEndTime);
        final TextInputEditText company = dialog.findViewById(R.id.textInputEditTextCompany);
        final TextInputEditText location = dialog.findViewById(R.id.textInputEditTextLocation);

        final Spinner spinner = dialog.findViewById(R.id.spinner);
        final Switch aSwitch = (Switch)dialog.findViewById(R.id.switch1);

        start_time.setText(starttime_work);
        end_time.setText(endtime_work);
        company.setText(company_work);
        location.setText(locationwork);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, spinnerValue);
        spinner.setAdapter(adapter);


        Button btnUpdate = dialog.findViewById(R.id.buttonSave);

        // set width for dialog
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (context.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();


        start_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                int CalendarHour, CalendarMinute;
                TimePickerDialog timepickerDialog;

                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);



                timepickerDialog = new TimePickerDialog(getContext(),
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

                                start_time.setText( hourOfDay+":"+String.format("%02d" ,minute)+":00"+" " +formats);

                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();


            }
        });

        end_time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                int CalendarHour, CalendarMinute;
                TimePickerDialog timepickerDialog;

                Calendar calendar = Calendar.getInstance();
                CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
                CalendarMinute = calendar.get(Calendar.MINUTE);



                timepickerDialog = new TimePickerDialog(getContext(),
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
                                //startTime.setText(String.format("%02d:%02d", hourOfDay ,minute)+formats);

                                end_time.setText( hourOfDay+":"+String.format("%02d" ,minute)+" " +format);


                            }
                        }, CalendarHour, CalendarMinute, false);
                timepickerDialog.show();


            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String alerts;
                String switchOn,switchOff;
                String valSwitch = "";

                if(aSwitch.isChecked()){
                    switchOn = aSwitch.getTextOn().toString();
                    valSwitch = switchOn;
                }else{
                    switchOff = aSwitch.getTextOff().toString();
                    valSwitch = switchOff;
                }

                alerts = spinner.getSelectedItem().toString();


                try {
                    Boolean res = db.updateWorkLoad(position,
                            company.getText().toString().trim(),
                            location.getText().toString().trim(),
                            start_time.getText().toString().trim(),
                            end_time.getText().toString().trim(),
                            alerts, valSwitch
                    );


                    if(res == true){
                        Toast.makeText(getContext(),"Data Updated Successfully",Toast.LENGTH_LONG).show();
                        updateWorkLoad();

                    }else{
                        //Toast.makeText(getContext()," Failed",Toast.LENGTH_LONG).show();
                    }
                    updateWorkLoad();
                    dialog.dismiss();
                    //Toast.makeText(getContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }

            }
        });

    }


    private void update(){
        // get all data from sqlite
        Cursor cursor = db.getDataClass("Select * From Table_Schedule where Day = '"+_day_+"' ");
        list.clear();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String subject = cursor.getString(1);
                String location = cursor.getString(2);
                String starttime = cursor.getString(3);
                String endtime = cursor.getString(4);
                String day = cursor.getString(5);
                String alerttime = cursor.getString(6);
                String starthour = cursor.getString(7);
                String formatstart = cursor.getString(8);
                String endhour = cursor.getString(9);
                String formatend = cursor.getString(10);
                int starttimeformat = cursor.getInt(11);
                int endtimeformat = cursor.getInt(12);
                String status = cursor.getString(13);
                String semesteryear = cursor.getString(14);
                String semester = cursor.getString(15);

                textView.setText("Your Class Schedule");
                textViewSem.setText(semester);
                list.add(new ClassSched_Diagram(id, subject, location, starttime, endtime,
                        day, alerttime, starthour, formatstart, endhour, formatend, starttimeformat, endtimeformat, status,semesteryear,semester));
            }
        }else {
            textView.setText("No Class Schedule Added");
        }
        adapter = new ListAdapter(getContext(), R.layout.class_sched_listview, list);
        listViewFriday.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    private void updateWorkLoad(){
        // get all data from sqlite
        Cursor cursor = db.getDataWorkLoad("Select * From Table_WorkLoad where Day = '"+_day_+"' ");
        list1.clear();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String subject = cursor.getString(1);
                String location = cursor.getString(2);
                String starttime = cursor.getString(3);
                String endtime = cursor.getString(4);
                String day = cursor.getString(5);
                String alerttime = cursor.getString(6);
                String starthour = cursor.getString(7);
                String formatstart = cursor.getString(8);
                String endhour = cursor.getString(9);
                String formatend = cursor.getString(10);
                int starttimeformat = cursor.getInt(11);
                int endtimeformat = cursor.getInt(12);
                String status = cursor.getString(13);

                ttime.setText("Your Work Schedule");
                list1.add(new WorkSched_Diagram(id, subject, location, starttime, endtime,
                        day, alerttime, starthour, formatstart, endhour, formatend, starttimeformat, endtimeformat, status));
            }
        }else{
            ttime.setText("No Work Schedule Added");
        }
        adapter1 = new ListAdapter1(getContext(), R.layout.class_workload_listview, list1);
        listViewWorkLoad.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();

        // get all data from sqlite
        Cursor cursor = db.getDataWorkLoad("Select * From Table_WorkLoad where Day = '"+_day_+"' ");
        list1.clear();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String subject = cursor.getString(1);
                String location = cursor.getString(2);
                String starttime = cursor.getString(3);
                String endtime = cursor.getString(4);
                String day = cursor.getString(5);
                String alerttime = cursor.getString(6);
                String starthour = cursor.getString(7);
                String formatstart = cursor.getString(8);
                String endhour = cursor.getString(9);
                String formatend = cursor.getString(10);
                int starttimeformat = cursor.getInt(11);
                int endtimeformat = cursor.getInt(12);
                String status = cursor.getString(13);

                ttime.setText("Your Work Schedule");
                list1.add(new WorkSched_Diagram(id, subject, location, starttime, endtime,
                        day, alerttime, starthour, formatstart, endhour, formatend, starttimeformat, endtimeformat, status));
            }
        }else{
            ttime.setText("No Work Schedule Added");
        }
        adapter1 = new ListAdapter1(getContext(), R.layout.class_workload_listview, list1);
        listViewWorkLoad.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();


        // get all data from sqlite
        Cursor cursor1 = db.getDataClass("Select * From Table_Schedule where Day = '"+_day_+"' ");
        list.clear();
        if (cursor1 != null && cursor1.getCount() > 0) {
            while (cursor1.moveToNext()) {
                int id = cursor1.getInt(0);
                String subject = cursor1.getString(1);
                String location = cursor1.getString(2);
                String starttime = cursor1.getString(3);
                String endtime = cursor1.getString(4);
                String day = cursor1.getString(5);
                String alerttime = cursor1.getString(6);
                String starthour = cursor1.getString(7);
                String formatstart = cursor1.getString(8);
                String endhour = cursor1.getString(9);
                String formatend = cursor1.getString(10);
                int starttimeformat = cursor1.getInt(11);
                int endtimeformat = cursor1.getInt(12);
                String status = cursor1.getString(13);
                String semesteryear = cursor1.getString(14);
                String semester = cursor1.getString(15);

                textView.setText("Your Class Schedule");
                textViewSem.setText(semester);
                list.add(new ClassSched_Diagram(id, subject, location, starttime, endtime,
                        day, alerttime, starthour, formatstart, endhour, formatend, starttimeformat, endtimeformat, status,semesteryear,semester));
            }
        }else {
            textView.setText("No Class Schedule Added");
        }
        adapter = new ListAdapter(getContext(), R.layout.class_sched_listview, list);
        listViewFriday.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    public void setUserVisibleHint(boolean isVisibleToUser) {
        //  ang use ani eh refresh ang fragment
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            getFragmentManager().beginTransaction().detach(this).attach(this).commit();
        }
    }
   /* private void saveFile(){

        strName = textViewSem.getText().toString();
        SharedPreferences sharedPreferences = activity.getSharedPreferences(FileName,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("name" ,strName);
        editor.commit();
        Toast.makeText(getContext(),"Save",Toast.LENGTH_LONG).show();
    }*/
}