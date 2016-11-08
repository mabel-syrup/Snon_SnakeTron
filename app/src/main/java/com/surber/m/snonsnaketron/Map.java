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
    private Paint mGridPaint;
    private Paint mBorderPaint;

    public int getMaxX(){return maxX;}
    public int getMaxY(){return maxY;}
    public int getSize(){return size;}

    private int maxX,maxY,size;

    public Map (Context context,int x,int y, int s){
        super(context);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        mBackgroundPaint.setARGB(100,248,249,249);

        mGridPaint= new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        mGridPaint.setColor(Color.RED);
        mGridPaint.setStrokeWidth(5);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(Color.WHITE);
        mGridPaint.setStrokeWidth(10);

        this.maxX = x;
        this.maxY = y;
        this.size=s;

    }

    public void onDraw(Canvas canvas){

        canvas.drawRect(maxX,maxY,maxX,maxY,mBackgroundPaint);
        canvas.drawLine (0,0,0,maxY,mBorderPaint);
        canvas.drawLine(0,0,maxX,0,mBorderPaint);
        canvas.drawLine(0,maxY,0,maxY,mBorderPaint);
        canvas.drawLine(maxX,maxY,maxX,maxY,mBorderPaint);


        //builds a grid for testing
       for (int y = 0; y <= maxY; y += size){
            canvas.drawLine(0f,y,maxX,y,mBorderPaint);
        }
        for (int x = 0; x <= maxX; x += size){
            canvas.drawLine(x,0,x,maxY,mGridPaint);
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
    public int getSquareSize(){
        return getSize();
    }
}
