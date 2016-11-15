package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by wo1624bu on 11/15/16.
 */

public class RelativeSnakeBuilder extends View implements SquareView  {

    private static String TAG = "SNAKEr";
    public RelativeSnake snake;

    int maxX;
    int maxY;

    public RelativeSnakeBuilder(Context context, int maxX, int maxY){
        super(context);

        this.maxX=maxX;
        this.maxY=maxY;

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

    public ArrayList<Point> getSegmentCoords () {
        ArrayList<Point> outPoints = new ArrayList<>();

        //Get actual head origin.
        Point origin = snake.getCoords();
        outPoints.add(origin);
        //Loop through each byte in the segments array.
        for (Byte segment : snake.getSegments()) {
            //Convert the segment byte to a direction.
            Point delta = byteToDelta(segment);
            //Offset the coordinate fo the previous segment by the direction.
            origin.offset(delta.x,delta.y);
            //Place into the Array.
            outPoints.add(origin);
        }
        return outPoints;
    }

    public Point byteToDelta (Byte direction) {
        int x;
        int y;
        switch (direction) {
            //Left
            case 0:
                x = -1;
                y = 0;
                break;
            //Up
            case 1:
                x = 0;
                y = -1;
                break;
            //Right
            case 2:
                x = 1;
                y = 0;
                break;
            //Down
            case 3:
                x = 0;
                y = 1;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }
        return new Point(x,y);
    }
}
