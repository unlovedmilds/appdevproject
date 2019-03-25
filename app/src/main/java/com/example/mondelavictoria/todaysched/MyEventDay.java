package com.example.mondelavictoria.todaysched;

import android.os.Parcel;
import android.os.Parcelable;

import com.applandeo.materialcalendarview.EventDay;

import java.util.Calendar;

/**
 * Created by Rey Dela Victoria on 9/12/2018.
 */

public class MyEventDay extends EventDay implements Parcelable {

    private static String mNote;
    private String mLocation;
    private String mMonth;
    MyEventDay(Calendar day, int imageResource, String note,String location, String month) {
        super(day, imageResource);
        mNote = note;
        mLocation=  location;
        mMonth = month;
    }

    String getNote() {
        return mNote;
    }
    public  void  setNote(String note) {
        this.mNote = note;
    }
    String getLocation() {
        return mLocation;
    }
    public  void  setLocation(String location) {
        this.mLocation = location;
    }
    public  void  setMonth(String month) {
        this.mMonth = month;
    }
    String getMonth() {
        return mMonth;
    }


    private MyEventDay(Parcel in) {
        super((Calendar) in.readSerializable(), in.readInt());
        mNote = in.readString();
        mLocation = in.readString();
        mMonth = in.readString();
    }
    public static final Creator<MyEventDay> CREATOR = new Creator<MyEventDay>() {
        @Override
        public MyEventDay createFromParcel(Parcel in) {
            return new MyEventDay(in);
        }
        @Override
        public MyEventDay[] newArray(int size) {
            return new MyEventDay[size];
        }
    };
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeSerializable(getCalendar());
        parcel.writeInt(getImageResource());
        parcel.writeString(mNote);
        parcel.writeString(mLocation);
        parcel.writeString(mMonth);
    }
    @Override
    public int describeContents() {
        return 0;
    }
}
