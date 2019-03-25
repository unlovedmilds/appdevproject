package com.example.mondelavictoria.todaysched;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by Mon Dela Victoria on 10/16/2018.
 */

public class AlertDialogClass extends AppCompatActivity{
    AlertDialog.Builder mAlertDlgBuilder;
    AlertDialog mAlertDialog;
    View mDialogView = null;
    Button mOKBtn, mCancelBtn;
    TextView textViewTask;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();

        // Build the dialog

        mAlertDlgBuilder = new AlertDialog.Builder(this);


        mDialogView = inflater.inflate(R.layout.dialog_layout, null);
        mOKBtn = (Button)mDialogView.findViewById(R.id.ID_Ok);

        textViewTask = (TextView)mDialogView.findViewById(R.id.textViewTask);
        mAlertDlgBuilder.setCancelable(false);
        mAlertDlgBuilder.setInverseBackgroundForced(true);
        mAlertDlgBuilder.setView(mDialogView);
        mAlertDialog = mAlertDlgBuilder.create();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String  time = sharedPreferences.getString("Time",MainActivity._start);
        //Toast.makeText(this,time,Toast.LENGTH_LONG).show();
        textViewTask.setText(time);
        mAlertDialog.show();

        mOKBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAlertDialog.dismiss();
                MainActivity.mp.pause();
                Intent intent = new Intent(AlertDialogClass.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
