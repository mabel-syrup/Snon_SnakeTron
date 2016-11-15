package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by nappy on 11/4/2016.
 */

public class Map extends View implements SquareView {

    private Paint mBackgroundPaint;
    private Paint mGridPaint;
    private Paint mBorderPaint;

    private Grid g = Grid.getInstance();

    public int getMaxX(){return maxX;}
    public int getMaxY(){return maxY;}
    public int getSize(){return size;}

    private int maxX,maxY,size;

    //private ArrayList<Apple> apples = new ArrayList<>();

    public Map (Context context,int x,int y, int s){
        super(context);
        mBackgroundPaint = new Paint();
        mBackgroundPaint.setStyle(Paint.Style.FILL);
        //Background is a light green, kinda resembling grass without being too intense
        mBackgroundPaint.setARGB(255,103,200,103);
        //100,248,249,249 - Original
        //255,140,200,219 - Blue grid

        mGridPaint= new Paint();
        mGridPaint.setStyle(Paint.Style.STROKE);
        //Slightly paler and brighter green
        mGridPaint.setARGB(200,174,242,174);
        //255,174,225,242 - blue grid highlight
        mGridPaint.setStrokeWidth(5);

        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setColor(Color.WHITE);
        mBorderPaint.setStrokeWidth(10);

        this.maxX = x;
        this.maxY = y;
        this.size=s;

        //TODO remove this test code
        int appleCount = 10;

        Random rnd =  new Random();

        for(int i = 0; i < appleCount; i++) {
            //apples.add(new Apple(getContext(), maxX, maxY, size));
            int ax = rnd.nextInt(g.tilesX - 1);
            int ay = rnd.nextInt(g.tilesY - 1);
            g.mapgrid[ax][ay] = g.APPLE_ID;
        }

    }

    //TODO use firebase to get all current snakes

    public void onDraw(Canvas canvas){



        canvas.drawRect(0,0,maxX,maxY,mBackgroundPaint);
        canvas.drawLine (0,0,0,maxY,mBorderPaint);
        canvas.drawLine(0,0,maxX,0,mBorderPaint);
        canvas.drawLine(0,maxY,0,maxY,mBorderPaint);
        canvas.drawLine(maxX,maxY,maxX,maxY,mBorderPaint);


        //builds a grid for testing
        for (int y = 0; y <= maxY; y += size){
             canvas.drawLine(0f,y,maxX,y,mGridPaint);
        }
        for (int x = 0; x <= maxX; x += size){
            canvas.drawLine(x,0,x,maxY,mGridPaint);
        }
        canvas.drawLine(0,0,maxX,0,mBorderPaint);
        canvas.drawLine(0,maxY,maxX,maxY,mBorderPaint);
        canvas.drawLine(0,0,0,maxY,mBorderPaint);
        canvas.drawLine(maxX,0,maxX,maxY,mBorderPaint);


        //Save the canvas with the current background.
        canvas.save();

        render(canvas);
    }

    //This is going to be called in the game loop.
    public void render (Canvas canvas) {
        //revert to base canvas state.  (Clears all entities from the background so we can re-render them)
        canvas.restore();
        //TODO cycle through snakes and render them

        for(int x = 0; x < g.tilesX; x++) {
            for (int y = 0; y < g.tilesY; y++) {
                if(g.mapgrid[x][y] == g.APPLE_ID) canvas.drawBitmap(g.APPLE_SPRITE,x * size, y * size, null);
            }
        }
    }
    //This is called in the game loop as well.
    //This handles all the calculations that happen each frame.
    public void update () {
        //TODO look for collisions
        //TODO keep a set amount of apples on the board at all times
        //This is probably where we'd keep track of the client's movements too.
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
