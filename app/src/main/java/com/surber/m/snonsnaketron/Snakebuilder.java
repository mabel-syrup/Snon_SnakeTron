package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by wo1624bu on 11/1/16.
 */

/*public class Snakebuilder extends View implements SquareView {

   private static String TAG = "SNAKEi";

    private int maxX;
    private int maxY;


    private float x,y;
    private final Paint mPaint = new Paint();
    protected int mSize = 60 ;

    public Stack<Segment> mSegments;

    private int mScore;

    public void reset (){
        mScore = 0;
        Segment s0 = mSegments.get(0);
        mSegments.empty();
    }
    @Override
    public int getsquareSpaceX() {
        return (int) getHeadX();
    }
    @Override
    public int getsquareSpaceY(){
        return (int) getHeadY();
    }

    public float getHeadX(){
        return mSegments.get(0).x;
    }

    public float getHeadY() {
        return mSegments.get(0).y;
    }

    public Snake getSnake() {
        return new Snake(mSegments,maxX,maxY,mSize);
    }


    @Override
    public int getSquareMaxX() {
        return 0;
    }

    @Override
    public int getSquareMaxY() {
        return 0;
    }

    @Override
    public int getSquareSize() {
        return 0;
    }


   // @IgnoreExtraProperties
    public class Segment implements SquareView {
        int x;
        int y;
        int size;
        public Segment(){}

        public Segment(int x, int y, int size){
            this.x=x;
            this.y=y;
            this.size= size;
        }
        //@Exclude
        @Override
        public int getSquareMaxX(){return x;}
        //@Exclude
        @Override
        public int getSquareMaxY(){return y;}
        @Override
        public int getSquareSize(){return size;}

        public Snakebuilder(Context context,Snake snake){
            super(context);
            mSegments=snake.getSegments();
            mPaint.setStyle(Paint.Style.FILL);
            mSize=snake.Size;
            mScore=snake.getScore();


        }

        public Snakebuilder(Context context,int maxX,int maxY){
            super(context);

            this.maxX=maxX;
            this.maxY=maxY;

            mSegments = new Stack<>();
            mSegments.add(new Segment(maxX,maxY,mSize));
            mPaint.setStyle(Paint.Style.FILL);

        }
       protected void onDraw(Canvas canvas){
           mPaint.setColor(Color.BLUE);
           canvas.drawCircle(x,y,mSize,mPaint);
       }
    }
}*/
