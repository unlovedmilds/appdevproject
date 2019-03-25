package com.example.mondelavictoria.todaysched;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by Rey Dela Victoria on 9/20/2018.
 */

public class Radio_Class extends Activity {

    static Radio_Class INSTANCE;
    String data="FirstActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {

        INSTANCE=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public static Radio_Class getActivityInstance()
    {
        return INSTANCE;
    }

    public String getData()
    {
        return this.data;
    }
}

