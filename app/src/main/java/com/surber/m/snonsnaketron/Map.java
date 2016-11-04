package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by nappy on 11/4/2016.
 */

public class Map extends View implements SquareView {

    private Paint mBackgroundPaint;
    private Paint mBorderPaint;

    public int getCenterX(){return centerX;}
    public int getCenterY(){return centerY;}

    private int centerX,centerY;

    public Map (Context context,int x,int y){
        super(context);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBackgroundPaint.setARGB(70,0,0,0);

        mBorderPaint= new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setARGB(255,255,255,255);

        this.centerX = x/10;
        this.centerY = y/10;

    }
    public void shift(int x,int y){
        centerX-=x;
        centerY-=y;
    }
    public void onDraw(Canvas canvas){

       canvas.drawRect(1f,1f,1f,1f,mBackgroundPaint);
       canvas.drawRect(centerY,centerX,centerY,centerX,mBorderPaint);

        //builds a grid for testing
        for (int y = 0; y <= centerY; y += 5){
            canvas.drawLine(0f,y,centerY,y,mBorderPaint);
        }
        for (int x = 0; x <= centerX; x += 5){
            canvas.drawLine(x,0f,centerX,x,mBorderPaint);
        }
    }
    @Override
    public int getSquareCenterX(){
        return getCenterX();
    }
    @Override
    public int getSquareCenterY(){
      return getCenterY();
    }
}
