package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.Random;

/**
 * Created by nappy on 11/5/2016.
 */

public class Apple extends View implements SquareView {

    private int size = 10;

    public boolean eaten =false;

    Paint mPaint;

    int x;
    int y;

    public Apple(Context context, float maxX, float maxY, int size){
        super(context);

        Random rnd = new Random();

        x = rnd.nextInt((int) maxX-1);
        y = rnd.nextInt((int) maxY-1);

        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawCircle(x,y,5f,mPaint);
    }
    @Override
    public String toString(){return "x="+ x + " y="+ y+ " eaten?" + eaten;}

    @Override
    public int getSquareMaxX(){
        return x;}
    @Override
    public  int getSquareMaxY(){
        return y;}
    @Override
    public int getSize(){
        return size;}


}
