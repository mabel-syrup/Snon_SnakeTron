package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by wo1624bu on 11/1/16.
 */

public class Snakebuilder extends View implements SquareView {

   private static String TAG = "SNAKEi";

    private int maxX;
    private int maxY;

    private float x,y;
    private final Paint mPaint = new Paint();
    protected int mSize = 20;

    @Override
    public int getSquareMaxX() {
        return 0;
    }

    @Override
    public int getSquareMaxY() {
        return 0;
    }

    @Override
    public int getSize() {
        return 0;
    }
    public Snakebuilder(Context context,int maxX,int maxY){
        super(context);

        this.maxX=maxX;
        this.maxY=maxY;
    }
}
