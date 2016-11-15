package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;

import java.util.Random;

/**
 * Created by nappy on 11/5/2016.
 */

public class Apple extends View implements SquareView {

    private int size;

    public boolean eaten =false;

    private Bitmap sprite;

    Paint mPaint;

    int x;
    int y;

    public Apple(Context context, float maxX, float maxY, int size){
        super(context);
        Grid g = Grid.getInstance();
        Random rnd = new Random();

        int fx = rnd.nextInt((int) maxX-1);
        int fy = rnd.nextInt((int) maxY-1);
        x = (int)(fx / size) * size;
        y = (int)(fy / size) * size;

        int tX = rnd.nextInt(g.tilesX - 1);
        int tY = rnd.nextInt(g.tilesY - 1);

        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.RED);

        //sprite = BitmapFactory.decodeResource(getResources(),R.drawable.apple_128);
        //sprite = Bitmap.createScaledBitmap(sprite,size,size,false);


        g.mapgrid[tX][tY] = g.APPLE_ID;
    }

    public Point getCoords () {
        return new Point(x,y);
    }

    public Bitmap render() {
        return sprite;
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
    public int getSquareSize(){
        return size;}


}
