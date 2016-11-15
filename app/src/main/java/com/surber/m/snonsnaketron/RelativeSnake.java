package com.surber.m.snonsnaketron;

import android.graphics.Point;

import java.util.Stack;

/**
 * Created by wo1624bu on 11/15/16.
 */

public class RelativeSnake {

    Point head;
    Stack<Byte> segments;

    public Point getCoords(){
        return head;
    }

    public Stack<Byte> getSegments () {
        return segments;
    }

    public void extend(Byte extension) {
        head = move(head,extension);
        segments.add(extension);
    }

    public void update(Byte direction) {
        head = move(head,direction);
        segments.push(direction);
    }

    private Point move (Point point, Byte direction) {
        int dX;
        int dY;
        switch (direction) {
            //Left
            case 0:
                dX = -1;
                dY = 0;
                break;
            //Up
            case 1:
                dX = 0;
                dY = -1;
                break;
            //Right
            case 2:
                dX = 1;
                dY = 0;
                break;
            //Down
            case 3:
                dX = 0;
                dY = 1;
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
