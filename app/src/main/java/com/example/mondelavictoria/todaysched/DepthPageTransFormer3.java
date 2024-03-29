package com.example.mondelavictoria.todaysched;

import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

/**
 * Created by Rey Dela Victoria on 9/20/2018.
 */

public class DepthPageTransFormer3 implements ViewPager.PageTransformer{

    @Override
    public void transformPage(View page, float position) {

        page.setTranslationX(-position*page.getWidth());
        page.setCameraDistance(20000);

        if (position < 0.5 && position > -0.5){
            page.setVisibility(View.VISIBLE);
        }
        else {
            page.setVisibility(View.INVISIBLE);
        }



        if (position < -1){     // [-Infinity,-1)
            // This page is way off-screen to the left.
            page.setAlpha(0);

        }
        else if (position <= 0 ){    // [-1,0]
            page.setAlpha(1);
            page.setRotationX(180*(1-Math.abs(position)+1));
            Log.e("HORIZONTAL", "position <= 0     " + (180 * (1 - Math.abs(position) + 1)));

        }
        else if (position <= 1){    // (0,1]
            page.setAlpha(1);
            page.setRotationX(-180*(1-Math.abs(position)+1));
            Log.e("HORIZONTAL", "position <= 1     " + (-180 * (1 - Math.abs(position) + 1)));

        }
        else {    // (1,+Infinity]
            // This page is way off-screen to the right.
            page.setAlpha(0);

        }


    }
}
