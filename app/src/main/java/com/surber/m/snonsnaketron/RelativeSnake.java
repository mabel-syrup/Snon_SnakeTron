package com.surber.m.snonsnaketron;

import android.graphics.Color;
import android.graphics.Point;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * Created by wo1624bu on 11/15/16.
 */

public class RelativeSnake {

    Point head;
    Queue<String> segments = new LinkedList<>();
    String queuedDirection = "Stationary";
    long speed = 100;
    long timeHolder = 0;
    int color = Color.BLACK;
    boolean isAI = true;

    public Point getCoords(){
        return head;
    }

    public Queue<String> getSegments () {
        return segments;
    }

    public void reset(Point resetLoc) {
        head = resetLoc;
        segments.clear();
        speed = 100;
        timeHolder = 0;
        extend("Left");
        extend("Left");
    }

    public void extend(String extension) {
        head = move(head,extension);
        segments.add(extension);
    }

    public void update(String direction) {
        head = move(head,direction);
        segments.add(direction);
        segments.remove();
    }

    //Provides a fake movement to see what the tile contains.
    public Point checkMovement(String direction) {
        Point testMovement = new Point(head.x,head.y);
        return move(testMovement,direction);
    }

    private Point move (Point point, String direction) {
        int dX;
        int dY;
        switch (direction) {
            //Left
            case "Left":
                dX = 1;
                dY = 0;
                break;
            //Up
            case "Up":
                dX = 0;
                dY = 1;
                break;
            //Right
            case "Right":
                dX = -1;
                dY = 0;
                break;
            //Down
            case "Down":
                dX = 0;
                dY = -1;
                break;
            default:
                dX = 0;
                dY = 0;
                break;
        }
        point.offset(dX,dY);
        return point;
    }

}
