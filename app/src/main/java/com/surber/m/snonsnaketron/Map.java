package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * Created by nappy on 11/4/2016.
 */

public class Map extends View implements SquareView {

    private Paint mBackgroundPaint;
    private Paint mBorderPaint;

    public int getMaxX(){return maxX;}
    public int getMaxY(){return maxY;}

    private int maxX,maxY;

    public Map (Context context,int x,int y){
        super(context);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mBackgroundPaint.setARGB(70,0,0,0);

        mBorderPaint= new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(Color.RED);
        mBorderPaint.setStrokeWidth(10);

        this.maxX = x;
        this.maxY = y;

    }

    public void onDraw(Canvas canvas){
        canvas.drawCircle(5f,5f,5f,mBorderPaint);

       canvas.drawRect(1f,1f,1f,1f,mBackgroundPaint);
       canvas.drawRect(maxY,maxX,maxY,maxX,mBorderPaint);

        //builds a grid for testing
        for (int y = 0; y <= maxY; y += 5){
            canvas.drawLine(0f,y,maxY,y,mBorderPaint);
        }
        for (int x = 0; x <= maxX; x += 5){
            canvas.drawLine(x,0f,maxX,x,mBorderPaint);
        }
    }
    @Override
    public int getSquareMaxX(){
        return getMaxX();
    }
    @Override
    public int getSquareMaxY(){
      return getMaxY();
    }
    @Override
    public int getSize(){
        return getSize();
    }
}
