package com.surber.m.snonsnaketron;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by wo1624bu on 11/1/16.
 */

public class Snake {

    Stack<Byte> segments = new Stack<>();
    Byte X = 0;
    Byte Y = 0;

    public Stack<Byte> update(Byte move) {
        segments.push(move);
        return segments;
    }

    public Stack<Byte> grow(Byte move) {
        segments.add(move);
        return segments;
    }

}
