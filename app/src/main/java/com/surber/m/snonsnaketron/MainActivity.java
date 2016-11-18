package com.surber.m.snonsnaketron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

    private static String TAG = "SNAKE_ACTIVITY";

    private static Map mMap;


    private float maxpixelsX;
    private float maxpixelsY;

    private int squaresmaxX;
    private int squaremaxY;

    private int size = 60;

    public int mapsquares[][];
    View.OnTouchListener mTouchListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFrame = (FrameLayout) findViewById(R.id.activity_main);

       //still need a workable snake
        mTouchListener = new View.OnTouchListener(){
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                switch (motionEvent.getActionMasked()){
                    case MotionEvent.ACTION_MOVE:{
                        int touchX= (int) motionEvent.getX();
                        int touchY= (int) motionEvent.getY();

                    }
                    case MotionEvent.ACTION_DOWN:{
                        mFrame.invalidate();
                    }
                }
                return true;
            }
        };

    }

    public void onWindowFocusChanged(boolean hasFocus){
        //gets the screen size
        maxpixelsX=mFrame.getWidth();
        maxpixelsY= mFrame.getHeight();
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
