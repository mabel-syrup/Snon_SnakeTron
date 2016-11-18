package com.surber.m.snonsnaketron;

import java.util.ArrayList;
import java.util.Stack;

/**
 * Created by nappy on 11/5/2016.
 */

public class Snake {


    Stack<TylerDurden.Segments>segments;

    public int getScore() {
        return Score;
    }

    public void setScore(int score) {
        Score = score;
    }

    int Size;
    int Score;

    public Snake(){}

    public Stack<TylerDurden.Segments> getSegments(){return segments;}

    public void setSegments(Stack<TylerDurden.Segments> segments) {
        this.segments = segments;
    }
    public Snake(Stack<TylerDurden.Segments> segments,int size){
        this.segments=segments;
        this.Size=size;
    }
}
