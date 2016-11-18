package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by nappy on 11/4/2016.
 */

public class Map extends SurfaceView implements SurfaceHolder.Callback{

    public SurfaceHolder holder = getHolder();
    public Timer gameLoopTimer = new Timer();
    public GameLoop gameLoop = new GameLoop();

    private Paint mBackgroundPaint;
    private Paint mGridPaint;
    private Paint mBorderPaint;
    private Paint mSnakeSegmentPaint;

    private Grid g = Grid.getInstance();

    public int getMaxX(){return maxX;}
    public int getMaxY(){return maxY;}
    public int getSize(){return size;}

    private int maxX,maxY,size;

    int appleCount = 0;
    ArrayList<Point> appleTiles = new ArrayList<>();//List of tiles occupied by apples currently.
    ArrayList<Point> snakeTiles = new ArrayList<>();//List of tiles occupied by snakes currently.
                                                    //These will be used to check for collisions.

    int idealAppleCount = 4;

    Random rnd =  new Random();

    private ArrayList<RelativeSnake> snakes = new ArrayList<>();

    //private ArrayList<Apple> apples = new ArrayList<>();

    public Map (Context context,int x,int y, int s){
        super(context);

        //This will be replaced
        RelativeSnake rS = new RelativeSnake();
        rS.head = new Point((int)(g.tilesX / 2),(int)(g.tilesY / 2));
        snakes.add(rS);
        rS.reset(new Point((int)(g.tilesX / 2),(int)(g.tilesY / 2)));

        gameLoopTimer.schedule(gameLoop,0,17);
        gameLoop.run();
        holder.addCallback(this);
        System.out.println("I'm here!");
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

        mSnakeSegmentPaint = new Paint();
        mSnakeSegmentPaint.setStyle(Paint.Style.FILL);
        mSnakeSegmentPaint.setColor(Color.BLACK);
        mSnakeSegmentPaint.setStrokeWidth(10);

        this.maxX = x;
        this.maxY = y;
        this.size=s;
    }

    //TODO use firebase to get all current snakes

    public void render(){

        //System.out.println("Rendering");

        Canvas canvas = holder.lockCanvas();

        if(canvas == null) return;

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

        for(int x = 0; x < g.tilesX; x++) {
            for (int y = 0; y < g.tilesY; y++) {
                if(g.mapgrid[x][y] == g.APPLE_ID) canvas.drawBitmap(g.APPLE_SPRITE,x * size, y * size, null);
                if(g.mapgrid[x][y] == g.SNAKE_ID) canvas.drawRect((x * size) + 10,(y * size) + 10,((x+1) * size) - 10,((y+1) * size) - 10, mSnakeSegmentPaint);
            }
        }

        for(RelativeSnake rS : snakes) {
            int sX = rS.head.x;
            int sY = rS.head.y;
            canvas.drawRect(sX * size,sY * size,(sX + 1) * size, (sY + 1) * size, mBorderPaint);
        }

        holder.unlockCanvasAndPost(canvas);

        //System.out.println("Rendered.");

    }

    //This is called in the game loop as well.
    //This handles all the calculations that happen each frame.
    public void update (double elapsed) {
        appleCount = 0;
        appleTiles.clear();
        snakeTiles.clear();

        for(int x = 0; x < g.tilesX; x++) {
            for (int y = 0; y < g.tilesY; y++) {
                if(g.mapgrid[x][y] == g.APPLE_ID) {
                    appleCount++;  //Count apples
                    appleTiles.add(new Point(x,y));  //List all tiles occupied by apples.
                }
                //clear snakes for recalculation
                if(g.mapgrid[x][y] == g.SNAKE_ID) g.mapgrid[x][y] = 0;
            }
        }

        for(RelativeSnake rS : snakes) {
            ArrayList<Point> segmentTiles = RelativeSnakeBuilder.getSegmentCoords(rS);
            for(Point p:segmentTiles) {
                try {
                    System.out.println("Segment: " + p.toString());
                    snakeTiles.add(p);
                    g.mapgrid[p.x][p.y] = g.SNAKE_ID;
                } catch (ArrayIndexOutOfBoundsException oob) {
                    continue;
                }
            }
        }



        while(appleCount < idealAppleCount) {
            int ax = rnd.nextInt(g.tilesX - 1);
            int ay = rnd.nextInt(g.tilesY - 1);
            boolean ok = true;
            for(Point p : appleTiles) {
                //Not exactly the same tile as an apple.
                //Same column or row is ok.
                if(p.x == ax || p.y == ay){
                    ok = false;
                }
            }
            if(ok) {
                g.mapgrid[ax][ay] = g.APPLE_ID;
                appleTiles.add(new Point(ax,ay));
                appleCount++;
            }
        }

        //Debugging code to make apples "decay" over time so I can see their spawn behavior.
        if(rnd.nextInt(25) == 1) {
            Point p = appleTiles.get(rnd.nextInt(appleTiles.size()));
            g.mapgrid[p.x][p.y] = 0;
            for(RelativeSnake rS : snakes) {
                if(rnd.nextBoolean()) {
                    rS.update(getRandDirection());
                }
                else {
                    //rS.update(getRandDirection());
                    rS.extend(getRandDirection());
                }
                if(rS.head.x >= g.tilesX || rS.head.y >= g.tilesY || rS.head.x <= 0 || rS.head.y <= 0) rS.reset(new Point(9,11));
                //if(rnd.nextBoolean()) rS.extend((byte)rnd.nextInt(4));
                //else rS.update((byte)rnd.nextInt(4));
            }
        }



        //This is probably where we'd keep track of the client's movements too.
    }

    private String getRandDirection () {
        int dir = rnd.nextInt(4);
        switch (dir) {
            case 0:
                return "Up";
            case 1:
                return "Left";
            case 2:
                return "Down";
            case 3:
                return "Right";
            default:
                return "Left";
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("Surface Created!");
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("Surface Changed!");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("Surface Destroyed!");
    }

    private class GameLoop extends TimerTask {

        @Override
        public void run() {
            update(0);
            render();
        }
    }
}
