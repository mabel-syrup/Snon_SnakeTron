package com.surber.m.snonsnaketron;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by wo1624bu on 11/15/16.
 */
public class Grid {

    public Resources resources;

    public final int APPLE_ID = 1;
    public  final int SNAKE_ID= 2;

    public Bitmap APPLE_SPRITE;


    private static Grid ourInstance = new Grid();

    public static Grid getInstance() {
        return ourInstance;
    }

    private Grid() {}

    public int size;

    public int tilesX;
    public int tilesY;

    public int mapgrid[][];


    public Bitmap sprite (int ID) {
        Bitmap sprite = BitmapFactory.decodeResource(resources,ID);
        return Bitmap.createScaledBitmap(sprite,size,size,false);
    }

}
