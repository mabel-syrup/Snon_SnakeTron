//package com.surber.m.snonsnaketron;
//
//import android.content.Context;
//import android.graphics.Canvas;
//import android.graphics.Color;
//import android.graphics.Paint;
//import android.graphics.Point;
//
//import java.util.Stack;
//
//import static com.surber.m.snonsnaketron.R.drawable.snake;
//
///**
// * Created by in0418gq on 11/15/16.
// */
//
//public class TylerDurden implements SquareView {
//
//    private int startX;
//    private int startY;
//    private final Paint mPaint = new Paint();
//    protected int mSize=20;
//    private int mScore;
//
//    public Stack<Segments> segment;
//
//    public void reset(){
//        mScore = 0;
//        segment.empty();
//    }
//    public Snake getSnake(){
//        return new Snake(segment,mSize);
//    }
//
//    @Override
//    public int getSquareMaxX() {
//        return 0;
//    }
//
//    @Override
//    public int getSquareMaxY() {
//        return 0;
//    }
//
//    @Override
//    public int getSquareSize() {
//        return 0;
//    }
//
//    public class Segments {
//
//        byte x;
//        byte y;
//        byte size;
//
//
//        public Segments() {
//        }
//
//        public Segments(byte x, byte y, byte size) {
//            this.x = x;
//            this.y = y;
//            this.size = size;
//        }
//
//        public TylerDurden(Context context, Snake snake) {
//            super(context);
//            segment = snake.getSegments();
//            mPaint.setStyle(Paint.Style.FILL);
//            mPaint.setColor(Color.BLACK);
//            mSize = snake.Size;
//            mScore = snake.getScore();
//
//        }
//    }
//       public TylerDurden(Context context, int startX,int startY){
//           super(context);
//           this.startX = 0;
//           this.startY=0;
//
//           segment=new Stack<>();
//           segment.add(new Segments(startX,startY,mSize));
//           mPaint.setStyle(Paint.Style.FILL);
//       }
//
//        public void addSegmentToTheBegining(byte x, byte y){
//            segment.push(new Segments(x,y,mSize));
//        }
//        public void removelast(){
//            segment.removeElementAt(mSize);
//        }
//        @Override
//        protected void onDraw(Canvas canvas){
//            canvas.drawCircle(x,y,60,mPaint);
//        }
//
//}
