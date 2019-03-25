package com.example.mondelavictoria.todaysched;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Add_Event_Task extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__event__task);
        TextView textView = (TextView)findViewById(R.id.textView4);
        //textView.setEllipsize(TextUtils.TruncateAt.MARQUEE);

    }
}
