package com.example.mondelavictoria.todaysched;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Mon Dela Victoria on 6/27/2018.
 */

public class TabFragment extends Fragment {

    public  static TabLayout tabLayout;
    public  static ViewPager viewPager;
    public  static int int_items= 9;

    FragmentManager FM;
    FragmentTransaction FT;
    MainActivity mainActivity = new MainActivity();

    int x = 0;
    boolean isLastPageSwiped;
    int counterPageScroll;
    DataBase_Helper db;
    String val;
    String s;

    public TabFragment() {
        // Required empty public constructor
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_tab,null);
        tabLayout=(TabLayout)v.findViewById(R.id.tabs);
        viewPager=(ViewPager)v.findViewById(R.id.viewpager);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        //img = sharedPreferences.getString("image",Signin_Activity.imgs);
        val = sharedPreferences.getString("transition",MainActivity.transition);

        db = new DataBase_Helper(getContext());
        // get all data from sqlite
        Cursor cursor = db.getDataTransition("Select * From Table_Transition");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                s = cursor.getString(0);
            }
            val = s;
            //Toast.makeText(getContext(),val,Toast.LENGTH_LONG).show();
        }else{
            val = "Transition Default";
        }
        //Toast.makeText(getContext(),val,Toast.LENGTH_LONG).show();
        //val = "Transition_Default";

        if( val.equals("Transition Circular1")){
            //set an adpater
            viewPager.setPageTransformer(true, new DepthPageTransformer2());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        }else if( val.equals("Transition Circular2")){
            //set an adpater
            viewPager.setPageTransformer(true, new DepthPageTransformer());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        } else if( val.equals("Transition Circular3")){
            //set an adpater
            viewPager.setPageTransformer(true, new DeathPagetransFormer1());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        } else if( val.equals("Transition Circular4")){
            //set an adpater
            viewPager.setPageTransformer(true, new DepthPageTransFormer3());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        } else if( val.equals("Transition Circular5")){
            //set an adpater
            viewPager.setPageTransformer(true, new DepthPageTransformer());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        }else if( val.equals("Transition Default")){
            //set an adpater
            //viewPager.setPageTransformer(true, new DepthPageTransformer());
            viewPager.setAdapter(new MyAdapter( getChildFragmentManager()));
        }


        //inig open nako sa app , eh open nya ang tab fragment which specific current day of week
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        switch (day) {

            case Calendar.MONDAY:
                viewPager.setCurrentItem(0);
                break;
            case Calendar.TUESDAY:
                viewPager.setCurrentItem(1);
                break;

            case Calendar.WEDNESDAY:
                viewPager.setCurrentItem(2);
                break;

          case Calendar.THURSDAY:
                viewPager.setCurrentItem(3);
              break;

            case Calendar.FRIDAY:
                viewPager.setCurrentItem(4);
                break;

            case Calendar.SATURDAY:
                viewPager.setCurrentItem(5);
                break;


            case Calendar.SUNDAY:
                viewPager.setCurrentItem(6);
                break;

        }

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
               // Animation animation = AnimationUtils.loadAnimation(getContext(),R.anim.transition);
                tabLayout.setupWithViewPager(viewPager);



            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int currentPosition=0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (position == 6 && positionOffset == 0 && !isLastPageSwiped){
                    if(counterPageScroll != 0){
                        isLastPageSwiped=true;
                        //Next Activity here
                        viewPager.setCurrentItem(0,false);

                        //FM = getFragmentManager();
                        //FT = FM.beginTransaction();
                        //FT.replace(R.id.container, new TabFragment()).commit();
                    }
                    counterPageScroll++;
                } else if (position == 0 && positionOffset == 0 && !isLastPageSwiped){
                    if(counterPageScroll != 0){
                        isLastPageSwiped=true;
                        //Next Activity here
                        viewPager.setCurrentItem(5,false);

                    }
                    counterPageScroll++;
                }else{
                    counterPageScroll=0;
                    isLastPageSwiped=false;
                }

            }

            @Override
            public void onPageSelected(int position) {

             /* if(position == 0){
                    viewPager.setCurrentItem( 7,false);
                }else if( position == 8){
                    viewPager.setCurrentItem(1,false);
                }*/

            }

            @Override
            public void onPageScrollStateChanged(int state) {


               /* int_items = viewPager.getAdapter().getCount();
                if (state == ViewPager.OVER_SCROLL_ALWAYS) {
                    if (currentPosition == 6 && state == ViewPager.SCROLL_INDICATOR_RIGHT) {
                        viewPager.setCurrentItem(0, false);
                        currentPosition++;
                    }

                }*/

            }
        });




        return v;
    }

    @Override
    public void onResume() {

        Cursor cursor = db.getDataTransition("Select * From Table_Transition");

        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String ss = cursor.getString(0);
                val = ss;
            }

            //Toast.makeText(getContext(),val,Toast.LENGTH_LONG).show();
        }else{
            val = "Transition Default";
        }
        super.onResume();
    }
}
