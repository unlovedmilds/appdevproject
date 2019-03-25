package com.example.mondelavictoria.todaysched;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class History extends AppCompatActivity {

    DataBase_Helper db;
    ListAdapterHistory adapter = null;
    ListView listViewHistory;
    ArrayList<ClassHistory_Diagram> list;

    private BottomSheetBehavior mBottomSheetBehavior;
    FloatingActionButton floatingActionButton,floatingActionButton1;
    AppCompatActivity activity;
    ImageButton button_add_event;

    TextView textViewDate,textViewSem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = new DataBase_Helper(this);
        listViewHistory = (ListView) findViewById(R.id.listViewHistory);
        list = new ArrayList<>();

        textViewDate = (TextView)findViewById(R.id.textViewDate);
        textViewSem = (TextView)findViewById(R.id.textViewSem);


        Cursor cursor = db.getDataClass("Select * From Table_History ");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id  =  cursor.getInt(0);
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

                //Toast.makeText(this, semesteryear+semester, Toast.LENGTH_SHORT).show();

                list.add(new ClassHistory_Diagram(id,subject, location, starttime, endtime,
                        day,alerttime,starthour,formatstart,endhour,formatend,starttimeformat,endtimeformat,status,semesteryear,semester ));

                textViewSem.setText(semester);
                textViewDate.setText(semesteryear);

            }
        }
        adapter = new ListAdapterHistory(getApplicationContext(), R.layout.history_griview_layout, list);
        listViewHistory.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        listViewHistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int _id = ((ClassHistory_Diagram) adapter.getItem(position)).getId();
                String starttime_his = ((ClassHistory_Diagram) adapter.getItem(position)).getStartTime();
                String endtime_his = ((ClassHistory_Diagram) adapter.getItem(position)).getEndTime();
                String subject_his = ((ClassHistory_Diagram) adapter.getItem(position)).getSubject();
                String location_his = ((ClassHistory_Diagram) adapter.getItem(position)).getLocation();
                String year_his = ((ClassHistory_Diagram) adapter.getItem(position)).getYear();
                String semester_his = ((ClassHistory_Diagram) adapter.getItem(position)).getSemester();
                textViewSem.setText(semester_his);
                textViewDate.setText(year_his);
            }
        });

        final View bottomSheet = findViewById(R.id.bottom_sheet);

        button_add_event = (ImageButton)findViewById(R.id.button_add_event);
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floating);
        floatingActionButton1 = (FloatingActionButton)findViewById(R.id.floating1);
        activity = new AppCompatActivity();
        mBottomSheetBehavior = BottomSheetBehavior.from(bottomSheet);



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

                //Intent intent = new Intent(getApplicationContext(),Add_WorkLoad.class);
                //startActivity(intent);
                //Toast.makeText(getContext(),"Ok",Toast.LENGTH_LONG).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                boolean res = db.deleteDataAllHistory();
                if(res == true) {

                    Toast.makeText(getApplicationContext(), "All Data History  Deleted", Toast.LENGTH_LONG).show();


                }else{
                    Toast.makeText(getApplicationContext(), "No Data History Found", Toast.LENGTH_LONG).show();
                }
                refresh();
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

    }
    public void refresh(){

        Cursor cursor = db.getDataClass("Select * From Table_History ");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                int id  =  cursor.getInt(0);
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

                //Toast.makeText(getContext(), day, Toast.LENGTH_SHORT).show();

                list.add(new ClassHistory_Diagram(id,subject, location, starttime, endtime,
                        day,alerttime,starthour,formatstart,endhour,formatend,starttimeformat,endtimeformat,status,semesteryear,semester ));

                textViewSem.setText(semester);
                textViewDate.setText(semesteryear);

            }
        }
        adapter = new ListAdapterHistory(getApplicationContext(), R.layout.history_griview_layout, list);
        listViewHistory.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Intent intent = new Intent(History.this,History.class);
        startActivity(intent);
        finish();

    }

}
