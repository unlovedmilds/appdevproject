package com.example.mondelavictoria.todaysched;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    android.app.AlertDialog.Builder mAlertDlgBuilder;
    android.app.AlertDialog mAlertDialog;
    View mDialogView = null;

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;

    boolean ok = false;
    public static String radio="";
    public  String radio_delete="";
    String Start_Date;
    String eventdate;
    int i = 0;
    boolean showDialog = false;

    TextView textView;
    private static final int time_interval = 2000;;
    private long mback;
    //public   Switch switch1,switch2,switch3;

    FragmentManager FM;
    FragmentTransaction FT;
     String dateString1,dateEvent;
    public static String _start;
    String newTime;
    public Thread t;
    public static String transition;

    TextToSpeech toSpeech;
    NotificationCompat.Builder builder1;
    NotificationManager notificationManager;
    AlarmManager alarmManager;
    PendingIntent broadcast;

    String dayLongName;
    String status_,statuswork;
    RadioButton r1,r2;

    boolean b = false;
    boolean oke = false;
    int result;
    long date;
    int viewpagersecondcount=0;

    public static MediaPlayer mp;

    PendingIntent intent;

    DataBase_Helper db;
    public MainActivity() {
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db = new DataBase_Helper(getApplicationContext());

        textView = (TextView)findViewById(R.id.scroll);




        long date_ = System.currentTimeMillis();
        SimpleDateFormat sdf_ = new SimpleDateFormat("dd MMMM yyyy");
        String dateString_event = sdf_.format(date_);
        textView.setText(dateString_event);
        dateEvent = textView.getText().toString();


        //new PageChangeListener();
        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.container, new TabFragment()).commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle("");
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.drawable.t);

       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

       //mao ni current  day
        Calendar sCalendar = Calendar.getInstance();
        dayLongName = sCalendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());

       /* switch (dayLongName) {
            case "Sunday":
                TabFragment.viewPager.setCurrentItem(6);
                break;
            case "Monday":
                TabFragment.viewPager.setCurrentItem(0);
                break;
            case "Tuesday":
                TabFragment.viewPager.setCurrentItem(1);
                break;
            case "Wednesday":
                TabFragment.viewPager.setCurrentItem(2);
                break;
            case "Thursday":
                TabFragment.viewPager.setCurrentItem(3);
                break;
            case "Friday":
                TabFragment.viewPager.setCurrentItem(4);
                break;
            case "Saturday":
                TabFragment.viewPager.setCurrentItem(5);
                break;


        }*/



       t = new Thread() {
            @Override
            public void run() {

                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                Intent notificationIntent = new Intent(getApplicationContext(), AlarmReceiver.class);
                broadcast = PendingIntent.getBroadcast(getApplicationContext(), 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @RequiresApi(api = Build.VERSION_CODES.N)
                            @Override
                            public void run() {


                                //AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);

                                Toolbar tdate = (Toolbar) findViewById(R.id.toolbar);
                                date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("h:mm:ss a");//mao ni  current time
                                dateString1 = sdf.format(date);
                                tdate.setSubtitle(dateString1);
                                tdate.setTitleTextColor(Color.BLACK);


                                Cursor cursor = db.getAllData(); // gi koha nako ang tanan data sa table na class didto sa sqlite database
                                if (cursor != null && cursor.getCount() > 0) {
                                    while (cursor.moveToNext()) {

                                        int _id = cursor.getInt(0);
                                        String _daily = cursor.getString(1);
                                        final String _location = cursor.getString(2);
                                         _start = cursor.getString(3);
                                        String _end = cursor.getString(4);
                                        String _dates = cursor.getString(5);
                                        String _alert = cursor.getString(6);
                                        String _h = cursor.getString(7);
                                        String _f1 = cursor.getString(8);
                                        String _h_ = cursor.getString(9);
                                        String _f1_ = cursor.getString(10);
                                        int stf = cursor.getInt(11);
                                        int etf = cursor.getInt(12);
                                        status_ = cursor.getString(13);

                                        Date d = null;
                                        try {
                                            d = sdf.parse(dateString1);

                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(d);
                                            cal.add(Calendar.MINUTE, Integer.valueOf(_alert));//gi koha nako ang  current  minutes og gi  minus sa value na alert na naka save sa database
                                            newTime = sdf.format(cal.getTime());

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        if (newTime.equals(_start) && _dates.equals(dayLongName)) { //gi check nako if  equal sila

                                            showDialog = true;

                                            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                            sharedPreferences.edit().putString("Time",_start).apply();

                                            t.interrupt();
                                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, broadcast);//these methods will fire even if the device is in Doze or Sleep mode.

                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.endtime);

                                            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                                            bigTextStyle.bigText("Location:" + _location);
                                            bigTextStyle.setBigContentTitle("Your Task Today");
                                            bigTextStyle.setSummaryText("By:Scheduler Management");

                                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class); // Replace ACTIVITY_TO_BE_DISPLAYED to Activity to which you wanna show

                                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                            //gosto nako ma click ang notification bar
                                            Bundle bundle = new Bundle();
                                            bundle.putString("buzz", "buzz");
                                            notificationIntent.putExtras(bundle);


                                            intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                                            //if i click notification bar ang ringtone mo stop automatically
                                            if(getIntent().getExtras()!=null){
                                                try {

                                                    mp.pause();
                                                }catch (Exception e){
                                                    e.getMessage();
                                                }
                                            }
                                            builder1 = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())

                                                    .setSmallIcon(R.drawable.dailytask)
                                                    .setAutoCancel(true)
                                                    //.setContentTitle("New Message Alert!")
                                                    //.setContentText("New Message Alert!")
                                                    .setLargeIcon(bitmap)
                                                    .setStyle(bigTextStyle);
                                            builder1.setContentIntent(intent);
                                            builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                                            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                            notificationManager.notify(0, builder1.build());


                                            toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                @Override
                                                public void onInit(int status) {


                                                    if (status == TextToSpeech.SUCCESS) {
                                                        result = toSpeech.setLanguage(Locale.ENGLISH);
                                                    } else {
                                                        //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                                    }

                                                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                        //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (status_.equals("On")) {
                                                            showDialog = true;
                                                            t.isAlive();
                                                            //toSpeech.setPitch(-1000);
                                                            toSpeech.setSpeechRate((float) .5);
                                                            //Toast.makeText(getContext(),"No Data Retrieve"+dateString1,Toast.LENGTH_LONG).show();

                                                            toSpeech.speak("You have a class today at" + dateString1 + "Location" + _location, TextToSpeech.QUEUE_FLUSH, null);

                                                            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                                            mp = MediaPlayer.create(getBaseContext(), alert);
                                                            if (mp != null) {
                                                                mp.setVolume(100, 100);
                                                                mp.start();

                                                                // dateString1 = "00:00";
                                                            } else {
                                                                mp.stop(); //This method stop the speak.
                                                            }

                                                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                @Override
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                }
                                                            });

                                                        }
                                                    }

                                                }


                                            });
                                        }

                                    }


                                }
                                /*if(showDialog) {

                                    Toast.makeText(getApplicationContext(), "Faild", Toast.LENGTH_LONG).show();


                                    LayoutInflater inflater = getLayoutInflater();

                                    // Build the dialog
                                    mAlertDlgBuilder = new android.app.AlertDialog.Builder(MainActivity.this);
                                    mDialogView = inflater.inflate(R.layout.dialog_layout, null);
                                    Button mOKBtn = (Button) mDialogView.findViewById(R.id.ID_Ok);
                                    Button mCancelBtn = (Button) mDialogView.findViewById(R.id.ID_Cancel);
                                    mAlertDlgBuilder.setCancelable(false);
                                    mAlertDlgBuilder.setInverseBackgroundForced(true);
                                    mAlertDlgBuilder.setView(mDialogView);
                                    mAlertDialog = mAlertDlgBuilder.create();
                                    mAlertDialog.show();

                                    mOKBtn.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            mAlertDialog.dismiss();
                                            finish();
                                        }
                                    });
                                }*/

                                Cursor cursor1 = db.getAllDataWork(); // gi koha nako ang tanan data sa table na class didto sa sqlite database
                                if (cursor1 != null && cursor1.getCount() > 0) {
                                    while (cursor1.moveToNext()) {

                                        int _id = cursor1.getInt(0);
                                        String _daily = cursor1.getString(1);
                                        final String _location = cursor1.getString(2);
                                        final String _startwork = cursor1.getString(3);
                                        String _end = cursor1.getString(4);
                                        String _dateswork = cursor1.getString(5);
                                        String _alert_ = cursor1.getString(6);
                                        String _h = cursor1.getString(7);
                                        String _f1 = cursor1.getString(8);
                                        String _h_ = cursor1.getString(9);
                                        String _f1_ = cursor1.getString(10);
                                        int stf = cursor1.getInt(11);
                                        int etf = cursor1.getInt(12);
                                        statuswork = cursor1.getString(13);

                                        Date d = null;
                                        try {
                                            d = sdf.parse(dateString1);

                                            Calendar cal = Calendar.getInstance();
                                            cal.setTime(d);
                                            cal.add(Calendar.MINUTE, Integer.valueOf(_alert_));//gi koha nako ang  current  minutes og gi  minus sa value na alert na naka save sa database
                                            newTime = sdf.format(cal.getTime());

                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }


                                        if (newTime.equals(_startwork) && _dateswork.equals(dayLongName)) { //gi check nako if  equal sila

                                            t.interrupt();
                                            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, broadcast);//these methods will fire even if the device is in Doze or Sleep mode.

                                            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.endtime);

                                            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                                            bigTextStyle.bigText("Location:" + _location);
                                            bigTextStyle.setBigContentTitle("Your Task Today");
                                            bigTextStyle.setSummaryText("By:Scheduler Management");

                                            Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class); // Replace ACTIVITY_TO_BE_DISPLAYED to Activity to which you wanna show

                                            notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                                            //gosto nako ma click ang notification bar
                                            Bundle bundle = new Bundle();
                                            bundle.putString("buzz", "buzz");
                                            notificationIntent.putExtras(bundle);

                                            intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                                            //if i click notification bar ang ringtone mo stop automatically
                                            if(getIntent().getExtras()!=null){
                                                try {
                                                    mp.pause();
                                                }catch (Exception e){
                                                    e.getMessage();
                                                }
                                            }

                                            builder1 = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())

                                                    .setSmallIcon(R.drawable.dailytask)
                                                    .setAutoCancel(true)
                                                    //.setContentTitle("New Message Alert!")
                                                    //.setContentText("New Message Alert!")
                                                    .setLargeIcon(bitmap)
                                                    .setStyle(bigTextStyle);
                                            builder1.setContentIntent(intent);
                                            builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                                            notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                            notificationManager.notify(0, builder1.build());


                                            toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                                                @Override
                                                public void onInit(int status) {


                                                    if (status == TextToSpeech.SUCCESS) {
                                                        result = toSpeech.setLanguage(Locale.ENGLISH);
                                                    } else {
                                                        //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                                    }

                                                    if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                                        //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                                                    } else {
                                                        if (statuswork.equals("On")) {

                                                            t.isAlive();
                                                            //toSpeech.setPitch(-1000);
                                                            toSpeech.setSpeechRate((float) .5);
                                                            //Toast.makeText(getContext(),"No Data Retrieve"+dateString1,Toast.LENGTH_LONG).show();

                                                            toSpeech.speak("You have a work today at" + dateString1 + "Location" + _location, TextToSpeech.QUEUE_FLUSH, null);

                                                            Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                                            mp = MediaPlayer.create(getBaseContext(), alert);
                                                            if (mp != null) {
                                                                mp.setVolume(100, 100);
                                                                mp.start();

                                                                // dateString1 = "00:00";
                                                            } else {
                                                                mp.stop(); //This method stop the speak.
                                                            }

                                                            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                                                @Override
                                                                public void onCompletion(MediaPlayer mp) {
                                                                    mp.release();

                                                                }
                                                            });

                                                        }
                                                    }

                                                }


                                            });
                                        }

                                    }

                                }

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();




        Cursor cursor2 = db.getAllDataEvents(); // gi koha nako ang tanan data sa table na class didto sa sqlite database
        if (cursor2 != null && cursor2.getCount() > 0) {
            while (cursor2.moveToNext()) {

                String what = cursor2.getString(0);
                String location = cursor2.getString(1);
                Start_Date = cursor2.getString(2);
                final String _start = cursor2.getString(3);
                String month = cursor2.getString(4);



                if (dateEvent.equals(Start_Date)) { //gi check nako if  equal sila

                    showDialog = true;
                    long l = 2*60*1000;
                    alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0, broadcast);//these methods will fire even if the device is in Doze or Sleep mode.

                    Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.endtime);

                    NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
                    bigTextStyle.bigText("Event:" + what);
                    bigTextStyle.setBigContentTitle("Your Event Today");
                    bigTextStyle.setSummaryText("By:Scheduler Management");

                    Intent notificationIntent = new Intent(getApplicationContext(), MainActivity.class); // Replace ACTIVITY_TO_BE_DISPLAYED to Activity to which you wanna show

                    notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

                    //gosto nako ma click ang notification bar
                    Bundle bundle = new Bundle();
                    bundle.putString("buzz", "buzz");
                    notificationIntent.putExtras(bundle);

                    intent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT);

                    //if i click notification bar ang ringtone mo stop automatically
                    if(getIntent().getExtras()!=null){
                        try {
                            mp.pause();
                        }catch (Exception e){
                            e.getMessage();
                        }
                    }

                    builder1 = (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())

                            .setSmallIcon(R.drawable.dailytask)
                            .setAutoCancel(true)
                            //.setContentTitle("New Message Alert!")
                            //.setContentText("New Message Alert!")
                            .setLargeIcon(bitmap)
                            .setStyle(bigTextStyle);
                    builder1.setContentIntent(intent);
                    builder1.setVibrate(new long[]{1000, 1000, 1000, 1000, 1000});

                    notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(0, builder1.build());


                    toSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
                        @Override
                        public void onInit(int status) {


                            if (status == TextToSpeech.SUCCESS) {
                                result = toSpeech.setLanguage(Locale.ENGLISH);
                            } else {
                                //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                            }

                            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                                //Toast.makeText(getContext(), "Feature not supported in your device", Toast.LENGTH_SHORT).show();
                            } else {
                                if (dateEvent.equals(Start_Date)) {

                                        showDialog = true;
                                    //toSpeech.setPitch(-1000);
                                    toSpeech.setSpeechRate((float) .5);
                                    //Toast.makeText(getContext(),"No Data Retrieve"+dateString1,Toast.LENGTH_LONG).show();

                                    toSpeech.speak("You have an Event Today on" + dateEvent , TextToSpeech.QUEUE_FLUSH, null);

                                    Uri alert = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
                                    mp = MediaPlayer.create(getBaseContext(), alert);
                                    if (mp != null) {
                                        mp.setVolume(100, 100);
                                        mp.start();

                                        // dateString1 = "00:00";
                                    } else {
                                        mp.stop(); //This method stop the speak.
                                    }

                                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                                        @Override
                                        public void onCompletion(MediaPlayer mp) {
                                            mp.release();

                                        }
                                    });

                                }
                            }

                        }


                    });
                }

            }

        }

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_transition) {
            // custom dialog
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.radiobutton_dialog);
            RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
            Button bt_yes = (Button)dialog.findViewById(R.id.buttonYes);
            RadioButton default_transition = (RadioButton)dialog.findViewById(R.id.default_transition);
            RadioButton r1 = (RadioButton)dialog.findViewById(R.id.r1);
            RadioButton r2 = (RadioButton)dialog.findViewById(R.id.r2);
            RadioButton r3 = (RadioButton)dialog.findViewById(R.id.r3);
            RadioButton r4 = (RadioButton)dialog.findViewById(R.id.r4);
            RadioButton r5 = (RadioButton)dialog.findViewById(R.id.r5);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId) {
                            radio = btn.getText().toString();


                            //Log.e("selected RadioButton->",btn.getText().toString());

                        }
                    }
                }

            });


            bt_yes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getApplicationContext(),radio,Toast.LENGTH_LONG).show();
                    Boolean result = db.insertTransition(radio);
                    if(result == true){
                        Toast.makeText(getApplicationContext(),"Faild",Toast.LENGTH_LONG).show();

                    }else{

                       Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                        updateTransition();
                        //Toast.makeText(getApplicationContext(),"Save",Toast.LENGTH_LONG).show();
                        dialog.dismiss();
                    }

                }
            });

            dialog.show();

            Display display =((WindowManager)getSystemService(this.WINDOW_SERVICE)).getDefaultDisplay();
            int width = display.getWidth();
            int height=display.getHeight();
            Log.v("width", width+"");
            dialog.getWindow().setLayout((5*width)/7,(3*height)/6);
        }
        else  if (id == R.id.action_settings) {
            //Intent intent = new Intent(MainActivity.this,Task_Event.class);
            //startActivity(intent);

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.settings);
            dialog.setTitle("");


            final Switch switch1 = (Switch)dialog.findViewById(R.id.switch1);
            final Switch switch2 = (Switch)dialog.findViewById(R.id.switch2);
            final Switch switch3 = (Switch)dialog.findViewById(R.id.switch3);
            Button btnSave = dialog.findViewById(R.id.buttonSave);


            Cursor cursor = db.getAllData(); // gi koha nako ang tanan data sa table na class didto sa sqlite database
            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {

                    int _id = cursor.getInt(0);
                    String _daily = cursor.getString(1);
                    final String _location = cursor.getString(2);
                    final String _start = cursor.getString(3);
                    String _end = cursor.getString(4);
                    String _dates = cursor.getString(5);
                    String _alert = cursor.getString(6);
                    String _h = cursor.getString(7);
                    String _f1 = cursor.getString(8);
                    String _h_ = cursor.getString(9);
                    String _f1_ = cursor.getString(10);
                    int stf = cursor.getInt(11);
                    int etf = cursor.getInt(12);
                    String _status_ = cursor.getString(13);

                    if(_status_.equals("On")){
                        switch1.setChecked(true);
                    }else{
                        switch1.setChecked(false);
                    }

                }
            }


            Cursor cursor1 = db.getAllDataWork(); // gi koha nako ang tanan data sa table na class didto sa sqlite database
            if (cursor1 != null && cursor1.getCount() > 0) {
                while (cursor1.moveToNext()) {

                    int _id = cursor1.getInt(0);
                    String _daily = cursor1.getString(1);
                    final String _location = cursor1.getString(2);
                    final String _startwork = cursor1.getString(3);
                    String _end = cursor1.getString(4);
                    String _dateswork = cursor1.getString(5);
                    String _alert_ = cursor1.getString(6);
                    String _h = cursor1.getString(7);
                    String _f1 = cursor1.getString(8);
                    String _h_ = cursor1.getString(9);
                    String _f1_ = cursor1.getString(10);
                    int stf = cursor1.getInt(11);
                    int etf = cursor1.getInt(12);
                    String statuswork_ = cursor1.getString(13);

                    if(statuswork_.equals("On")){
                        switch2.setChecked(true);
                    }else{
                        switch2.setChecked(false);
                    }



                }
            }


            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String statOff = "Off";
                    String statOn = "On";

                    if (switch1.isChecked()) {
                        db.updateClassAlarm(statOn);
                        Toast.makeText(getApplicationContext(),"Alarm On",Toast.LENGTH_SHORT).show();
                    }else {
                        db.updateClassAlarm(statOff);
                        Toast.makeText(getApplicationContext(),"Alarm Off",Toast.LENGTH_SHORT).show();
                    }
                    if (switch2.isChecked()) {
                        db.updateWorkAlarm(statOn);
                        Toast.makeText(getApplicationContext(),"Alarm On",Toast.LENGTH_SHORT).show();
                    }else {
                        db.updateWorkAlarm(statOff);
                        Toast.makeText(getApplicationContext(),"Alarm Off",Toast.LENGTH_SHORT).show();
                    }


                }
            });



            // set width for dialog
            int width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.95);
            // set height for dialog
            int height = (int) (getApplicationContext().getResources().getDisplayMetrics().heightPixels * 0.7);
            dialog.getWindow().setLayout(width, height);
            dialog.show();

        }
        else  if (id == R.id.action_event) {
            Intent intent = new Intent(MainActivity.this,Task_Event.class);
            startActivity(intent);
        }else  if (id == R.id.action_history) {
            Intent intent = new Intent(MainActivity.this,History.class);
            startActivity(intent);
        }else if(id == R.id.action_delete){

            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.delete_schedule);
            dialog.setTitle("");

            String[] spinnerValue = {"Monday", "Tuesday", "Wednesday","Thursday", "Friday","Saturday","Sunday","Reset"};
            String[] spinnerValue1 = {"January", "February", "March","April", "May","June","July","August",
            "September","October","November","December"};

            final Spinner spinner = dialog.findViewById(R.id.spinner);
            final ImageView imageView = dialog.findViewById(R.id.imageView);
            RadioGroup rg = (RadioGroup) dialog.findViewById(R.id.radio_group);
            r1 = (RadioButton)dialog.findViewById(R.id.radio1);
            r2 = (RadioButton)dialog.findViewById(R.id.radio2);


            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerValue);
            spinner.setAdapter(adapter);

            //ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerValue1);
            //spinner1.setAdapter(adapter1);

            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int childCount = group.getChildCount();
                    for (int x = 0; x < childCount; x++) {
                        RadioButton btn = (RadioButton) group.getChildAt(x);
                        if (btn.getId() == checkedId) {
                            radio_delete = btn.getText().toString();
                           // Toast.makeText(getApplicationContext(), radio_delete, Toast.LENGTH_SHORT).show();

                            //Log.e("selected RadioButton->",btn.getText().toString());

                        }
                    }
                }

            });


            Button btnSave = dialog.findViewById(R.id.buttonSave);

            // set width for dialog
            int width = (int) (getApplicationContext().getResources().getDisplayMetrics().widthPixels * 0.95);
            // set height for dialog
            int height = (int) (getApplicationContext().getResources().getDisplayMetrics().heightPixels * 0.7);
            dialog.getWindow().setLayout(width, height);
            dialog.show();



            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String alerts,alerts1;
                    String switchOn,switchOff;

                    alerts = spinner.getSelectedItem().toString();
                    //alerts1 = spinner1.getSelectedItem().toString();

                    //Toast.makeText(getApplicationContext(),alerts,Toast.LENGTH_LONG).show();


                    if(!r1.isChecked() && !r2.isChecked()){
                        Toast.makeText(getApplicationContext(),"Please,check the radio button",Toast.LENGTH_LONG).show();
                    }
                    else if(alerts.equals("Reset") && radio_delete.equals("Class Schedule")) {
                       boolean res = db.deleteDataAllClass();
                        if(res == true) {

                            Toast.makeText(getApplicationContext(), "All Data Class Sched Deleted", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "No Class Sched  Found", Toast.LENGTH_LONG).show();
                        }

                    }else  if(alerts.equals("Reset") && radio_delete.equals("Work Schedule")) {
                        boolean res = db.deleteDataAllWork();
                        if(res == true) {

                            Toast.makeText(getApplicationContext(), "All Data Work Sched Deleted", Toast.LENGTH_LONG).show();

                        }else{
                            Toast.makeText(getApplicationContext(), "No  Work Sched Found", Toast.LENGTH_LONG).show();
                        }
                    }

                    else if(radio_delete.equals("Class Schedule")  && !alerts.equals("Reset")) {
                        boolean delclass = db.deleteDataClass(alerts);
                        if(delclass) {
                            Toast.makeText(getApplicationContext(), alerts+" " + " Class Sched Deleted", Toast.LENGTH_LONG).show();
                        }
                        else if(!delclass){
                            Toast.makeText(getApplicationContext(), "No Record Found on"+" "+alerts+" " +"Class Sched", Toast.LENGTH_LONG).show();
                        }

                    }else  if(radio_delete.equals("Work Schedule")  && !alerts.equals("Reset")) {
                        boolean delwork = db.deleteDataWork(alerts);
                        if(delwork) {
                            Toast.makeText(getApplicationContext(), alerts+" " + " Work Sched Deleted", Toast.LENGTH_LONG).show();
                        }
                        else if(!delwork){
                            Toast.makeText(getApplicationContext(), "No Record Found on"+" "+alerts+" "+ "Work Sched ", Toast.LENGTH_LONG).show();
                        }
                    }


                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    final Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.events_delete);
                    dialog.setTitle("");

                    String[] spinnerValue1 = {"January", "February", "March","April", "May","June","July","August",
                            "September","October","November","December"};
                    final Spinner spinner1 = dialog.findViewById(R.id.spinner1);

                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, spinnerValue1);
                    spinner1.setAdapter(adapter1);

                    Button btnSave = dialog.findViewById(R.id.buttonSave);

                    // set width for dialog
                    int width = (int) (MainActivity.this.getResources().getDisplayMetrics().widthPixels * 0.95);
                    // set height for dialog
                    int height = (int) (MainActivity.this.getResources().getDisplayMetrics().heightPixels * 0.7);
                    dialog.getWindow().setLayout(width, height);
                    dialog.show();

                    btnSave.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String alerts = spinner1.getSelectedItem().toString();

                            boolean res = db.deleteDataEventsMonth(alerts);
                            if(res == true) {

                                Toast.makeText(getApplicationContext(), "All Data EVents Sched Deleted", Toast.LENGTH_LONG).show();

                            }else{
                                Toast.makeText(getApplicationContext(), "No Events Sched  Found", Toast.LENGTH_LONG).show();
                            }


                        }
                    });
                }
            });

        }

        return super.onOptionsItemSelected(item);
    }


    public void show() {
        if(showDialog){

            Toast.makeText(getApplicationContext(),"Faild",Toast.LENGTH_LONG).show();


            LayoutInflater inflater = getLayoutInflater();

            // Build the dialog
            mAlertDlgBuilder = new android.app.AlertDialog.Builder(this);
            mDialogView = inflater.inflate(R.layout.dialog_layout, null);
            Button mOKBtn = (Button)mDialogView.findViewById(R.id.ID_Ok);

            mAlertDlgBuilder.setCancelable(false);
            mAlertDlgBuilder.setInverseBackgroundForced(true);
            mAlertDlgBuilder.setView(mDialogView);
            mAlertDialog = mAlertDlgBuilder.create();
            mAlertDialog.show();

            mOKBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mAlertDialog.dismiss();
                    finish();
                }
            });

        }

        }




    @Override
    protected void onResume() {
        // get all data from sqlite
        Cursor cursor = db.getDataTransition("Select * From Table_Transition");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               String s = cursor.getString(0);
            }

        }
        updateTransition();
        super.onResume();
    }

    @Override
    public void onBackPressed() {

        try {
            mp.release();
            mp.stop();
            toSpeech.stop(); //This method stop the speak.
        }catch (Exception e){
            e.getMessage();
        }

        if(mback + time_interval > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else{
            Toast.makeText(this,"Click back again to exit",Toast.LENGTH_SHORT).show();
            mback = System.currentTimeMillis();

        }

       /* this.bck = true;
        Toast.makeText(this,"Please,click back again to exit",Toast.LENGTH_LONG).show();
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {

            }
        },200;*/

        FM = getSupportFragmentManager();
        FT = FM.beginTransaction();
        FT.replace(R.id.container, new TabFragment()).commit();


    }

    public void updateTransition(){
        Cursor cursor = db.getDataTransition("Select * From Table_Transition");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
               transition = cursor.getString(0);
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                sharedPreferences.edit().putString("transition", transition).apply();

            }
        }
    }
/*@Override
    public void onBackPressed() {
        //android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        if (FM.getBackStackEntryCount() > 1) {
            //Go back to previous Fragment
            FM.popBackStackImmediate();
        } else {
            //Nothing in the back stack, so exit
            super.onBackPressed();
        }
    }*/
}
