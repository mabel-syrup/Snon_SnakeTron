package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by wo1624bu on 11/15/16.
 */

public class RelativeSnakeBuilder  {



    public RelativeSnakeBuilder(Context context, int maxX, int maxY){

    }

    public static ArrayList<Point> getSegmentCoords (RelativeSnake snake) {
        ArrayList<Point> outPoints = new ArrayList<>();

        //Get actual head origin.
        Point temp = new Point(snake.getCoords().x, snake.getCoords().y);
        outPoints.add(new Point(temp.x, temp.y));

        Queue<String> segQ = reverseQueue(snake.getSegments());

        //Loop through each byte in the segments array.
        for (String segment : segQ) {
            //Convert the segment byte to a direction.
            Point delta = stringToDelta(segment);
            //Offset the coordinate fo the previous segment by the direction.
            temp = new Point(temp.x - delta.x,temp.y - delta.y);
            //Place into the Array.
            outPoints.add(new Point(temp.x, temp.y));
        }
        return outPoints;
    }

    private static Queue<String> reverseQueue (Queue<String> queue) {
        ArrayList<String> arrayList = new ArrayList<>();
        for(String s : queue) {
            arrayList.add(s);
        }
        for(int i = 0; i < arrayList.size() / 2; i++) {
            String temp = arrayList.get(i);
            arrayList.set(i,arrayList.get(arrayList.size() - i - 1));
            arrayList.set(arrayList.size() - i - 1,temp);
        }
        Queue<String> outQ = new LinkedList<>();
        for(String s: arrayList) {
            outQ.add(s);
        }
        return outQ;
    }

    public static Point stringToDelta (String direction) {
        int x;
        int y;
        switch (direction) {
            //Left
            case "Left":
                x = 1;
                y = 0;
                break;
            //Up
            case "Up":
                x = 0;
                y = 1;
                break;
            //Right
            case "Right":
                x = -1;
                y = 0;
                break;
            //Down
            case "Down":
                x = 0;
                y = -1;
                break;
            default:
                x = 0;
                y = 0;
                break;
        }
        return new Point(x,y);
    }
}
