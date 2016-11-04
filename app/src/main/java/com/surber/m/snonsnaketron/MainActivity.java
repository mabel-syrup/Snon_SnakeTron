package com.surber.m.snonsnaketron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

   FrameLayout mFrame;
   private static Map mMap;

    private float maxX;
    private float maxY;

    private int centerX;
    private int centerY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFrame = (FrameLayout) findViewById(R.id.activity_main);

        mMap= new Map(this,centerX,centerY);
        mFrame.addView(mMap);

    }
//does it automatically
    @Override
    public void onWindowFocusChanged(boolean hasFoucus){
        //gets the screen size
        maxX=mFrame.getWidth();
        maxY= mFrame.getHeight();
        //makes the canvas smaller than its natual window
        centerX=(int)maxX/2;
        centerY= (int)maxY/2;
    }





}
