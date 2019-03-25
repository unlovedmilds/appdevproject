package com.example.mondelavictoria.todaysched;

/**
 * Created by Rey Dela Victoria on 6/27/2018.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyAdapter  extends FragmentPagerAdapter {



    public MyAdapter(FragmentManager fm)
    {
        super(fm);

    }
    @Override
    public Fragment getItem(int position) {
      /* if(position == 0){
            return new Sunday_TabFragment();
        }
        else if(position == 1){
            return new Monday_TabFragment();
        }
        else if(position == 2){
            return new Tuesday_TabFragment();
        }
        else if(position == 3){
            return new Wednesday_TabFragment();
        }
        else if(position == 4){
            return new Thursday_TabFragment();
        }
        else if(position == 5){
            return new Friday_TabFragment();
        }
        else if(position == 6){
            return new Saturday_TabFragment();
        }
        else if(position == 7){
            return new Sunday_TabFragment();
        }
       else{
            return new Monday_TabFragment();
        }*/

        switch (position) {

            case 0:
                return new Monday_TabFragment();
            case 1:
                return new Tuesday_TabFragment();
            case 2:
                return new Wednesday_TabFragment();
            case 3:
                return new Thursday_TabFragment();
            case 4:
                return new Friday_TabFragment();
            case 5:
                return new Saturday_TabFragment();
            case 6:
                return new Sunday_TabFragment();


        }
        return null;
    }

    @Override
    public int getCount() {


        return 7;
    }

    public CharSequence getPageTitle(int position){
        switch (position){

         /* case 0:
                return "Sun";
            case 1:
                return "Mon";
            case 2:
                return "Tue";
            case 3:
                return "Wed";
            case 4:
                return "Thu";
            case 5:
                return "Fri";
            case 6:
                return "Sat";
            case 7:
                return "Sun";
            case 8:
                return "Mon";*/
            case 0:
                return "Mon";
            case 1:
                return "Tue";
            case 2:
                return "Wed";
            case 3:
                return "Thu";
            case 4:
                return "Fri";
            case 5:
                return "Sat";
            case 6:
                return "Sun";


        }

        return null;
    }

}
