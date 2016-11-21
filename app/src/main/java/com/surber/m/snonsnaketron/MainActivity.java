package com.surber.m.snonsnaketron;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity  {

    FrameLayout mFrame;
    TextView mGameOver;

    View.OnClickListener restartListener;

    private boolean running = true;

    private static String DEBUG_TAG = "SNAKE_ACTIVITY";

    private static Map mMap;


    private float maxpixelsX;
    private float maxpixelsY;
    private float aspect;

    private int squaresmaxX;
    private int squaremaxY;

    private int size = 60;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFrame = (FrameLayout) findViewById(R.id.activity_main);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        int action = MotionEventCompat.getActionMasked(event);

        int[] mFrameLoc = new int[2];
        mFrame.getLocationOnScreen(mFrameLoc);


        switch(action) {
            case (MotionEvent.ACTION_DOWN) :
                Log.d(DEBUG_TAG,"Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE) :
                Log.d(DEBUG_TAG,"Action was MOVE");
                return true;
            case (MotionEvent.ACTION_UP) :
                Log.d(DEBUG_TAG,"Action was UP");
                if(mMap == null) break;  //No map yet, so tapping a direction can't do anything anyway.
                System.out.println("mMap Y: " + mFrameLoc[1]);
                System.out.println("X: " + Math.abs(event.getX() - (maxpixelsX / 2)) * (aspect) + ", Y: " + Math.abs((event.getY() - mFrameLoc[1]) - (maxpixelsY / 2)));
                if((Math.abs(event.getX() - (maxpixelsX / 2)) * (aspect)) > Math.abs((event.getY() - mFrameLoc[1]) - (maxpixelsY / 2))) {
                    //We're further to a side than up or down, so we'll call it a side input.
                    if(event.getX() - (maxpixelsX / 2) < 0) {
                        System.out.println("Left?");
                        mMap.localDirection = "Right";
                    }
                    else if(event.getX() - (maxpixelsX / 2) > 0) {
                        System.out.println("Right?");
                        mMap.localDirection = "Left";
                    }
                } else {
                    if((event.getY() - mFrameLoc[1]) - (maxpixelsY / 2) < 0) {
                        System.out.println("Up?");
                        mMap.localDirection = "Down";
                    }
                    else if((event.getY() - mFrameLoc[1]) - (maxpixelsY / 2) > 0) {
                        System.out.println("Down?");
                        mMap.localDirection = "Up";
                    }
                }
                return true;
            case (MotionEvent.ACTION_CANCEL) :
                Log.d(DEBUG_TAG,"Action was CANCEL");
                return true;
            case (MotionEvent.ACTION_OUTSIDE) :
                Log.d(DEBUG_TAG,"Movement occurred outside bounds " +
                        "of current screen element");
                return true;
            default :
                return super.onTouchEvent(event);
        }
        return false;
    }

    public void onWindowFocusChanged(boolean hasFocus){
        //gets the screen size
        maxpixelsX=mFrame.getWidth();
        maxpixelsY= mFrame.getHeight();
        aspect = (maxpixelsY / maxpixelsX);
        //makes the canvas smaller than its natual window
        squaresmaxX=(int)maxpixelsX / size;
        squaremaxY= (int)maxpixelsY / size;


        Grid g = Grid.getInstance();
        g.size = size;
        g.resources = getResources();
        g.APPLE_SPRITE = g.sprite(R.drawable.apple_128);
        g.tilesX = squaresmaxX;
        g.tilesY = squaremaxY;
        g.mapgrid = new int[squaresmaxX][squaremaxY];




        mMap= new Map(this,(int)maxpixelsX,(int)maxpixelsY,size);
        mFrame.addView(mMap);
        //mMap.gameLoop();
    }




}
