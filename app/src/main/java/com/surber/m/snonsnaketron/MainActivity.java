package com.surber.m.snonsnaketron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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


    private float maxX;
    private float maxY;

    private int squaresmaxX;
    private int squaremaxY;

    private int size = 60;

    public int mapsquares[][];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mFrame = (FrameLayout) findViewById(R.id.activity_main);

    }
//does it automatically
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        //gets the screen size
        maxX=mFrame.getWidth();
        maxY= mFrame.getHeight();
        //makes the canvas smaller than its natual window
        squaresmaxX=(int)maxX / 60;
        squaremaxY= (int)maxY / 60;


        Grid g = Grid.getInstance();
        g.size = size;
        g.resources = getResources();
        g.APPLE_SPRITE = g.sprite(R.drawable.apple_128);
        g.tilesX = squaresmaxX;
        g.tilesY = squaremaxY;
        g.mapgrid = new int[squaresmaxX][squaremaxY];

        mMap= new Map(this,(int)maxX,(int)maxY,size);
        mFrame.addView(mMap);
    }




}
