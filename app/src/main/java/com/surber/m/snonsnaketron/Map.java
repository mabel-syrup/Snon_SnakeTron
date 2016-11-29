package com.surber.m.snonsnaketron;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
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

    private DatabaseReference db;



    private Paint mBackgroundPaint;
    private Paint mGridPaint;
    private Paint mBorderPaint;
    private Paint mSnakeSegmentPaint;
    private Paint mSnakeDotPaint;

    private Grid g = Grid.getInstance();

    private int maxX,maxY,size;

    int appleCount = 0;
    ArrayList<Point> appleTiles = new ArrayList<>();//List of tiles occupied by apples currently.
    ArrayList<Point> snakeTiles = new ArrayList<>();//List of tiles occupied by snakes currently.
                                                    //These will be used to check for collisions.

    int idealAppleCount = 4;
    int counter = 0;

    Random rnd =  new Random();

    private ArrayList<RelativeSnake> snakes = new ArrayList<>();

    long lastTimeMillis;

    public String localDirection = "Stationary";

    public Map (Context context,int x,int y, int s){
        super(context);
        FirebaseDatabase database=FirebaseDatabase.getInstance();
        db = database.getReference();

        //This will be replaced
        RelativeSnake rS = new RelativeSnake();
        snakes.add(rS);
        rS.isAI = false;

        if(rS.queuedDirection== "Stationary" )
        rS.reset(new Point((int)(g.tilesX / 2),(int)(g.tilesY / 2)));
        rS.queuedDirection = "Up";
        rS.color = Color.BLUE;

        RelativeSnake rSB = new RelativeSnake();
        snakes.add(rSB);
        rSB.isAI = true;
        rSB.id = 1;
        rSB.reset(new Point(5,5));
        rSB.queuedDirection =localDirection ;
        rSB.color = Color.RED;
        //db.child("rSB").setValue(rSB.queuedDirection);

        lastTimeMillis = System.currentTimeMillis();
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

        mSnakeDotPaint = new Paint();
        mSnakeDotPaint.setStyle(Paint.Style.FILL);
        mSnakeDotPaint.setColor(Color.BLACK);
        mSnakeDotPaint.setStrokeWidth(10);

        this.maxX = x;
        this.maxY = y;
        this.size=s;
    }

    //TODO use firebase to get all current snakes



    public void render(){

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
            mSnakeDotPaint.setColor(rS.color);
            canvas.drawRect(sX * size + 10,sY * size + 10,(sX + 1) * size - 10, (sY + 1) * size - 10, mSnakeDotPaint);
            ArrayList<Point> segmentTiles = RelativeSnakeBuilder.getSegmentCoords(rS);
            for(Point p : segmentTiles){
                canvas.drawRect(p.x * size + 20,p.y * size + 20,(p.x + 1) * size - 20, (p.y + 1) * size - 20, mSnakeDotPaint);
            }
        }

        holder.unlockCanvasAndPost(canvas);

    }

    //This is called in the game loop as well.
    //This handles all the calculations that happen each frame.
    public void update (long elapsed) {
        appleCount = 0;
        appleTiles.clear();
        snakeTiles.clear();



        //Checking movements before committing.
        //This is how we see if they died, grew, or moved.
        for(final RelativeSnake rS : snakes){

            if(!rS.isAI) rS.queuedDirection = localDirection;

            rS.timeHolder += elapsed;
            if(rS.timeHolder < rS.speed) {
                //System.out.println(rS.timeHolder + " is less than " + rS.speed);
                continue;
            }
            rS.timeHolder = 0;
            //The "AI" snake uses this to not kill itself right away.  Not going to be kept.
            Point check = rS.checkMovement(rS.queuedDirection);
            if(check.x >= g.tilesX || check.y >= g.tilesY || check.x < 0 || check.y < 0){
                rS.reset(new Point(9,11));  //Death!  (Replace with death method)
            }
            else if(g.mapgrid[check.x][check.y] == g.APPLE_ID) {
                rS.extend(rS.queuedDirection);  //Ate and apple.  Grow.
                rS.speed -= 10;  //Speed boost!
                g.mapgrid[check.x][check.y] = 0;  //Apple's gone now.
            }
            else if(g.mapgrid[check.x][check.y] == g.SNAKE_ID) {
                rS.reset(new Point(9,11));  //Death!  (Replace with death method)
            }
            if(rS.isAI) {
                Query getdir = db.child(String.valueOf("1"));
              // rS.queuedDirection = String.valueOf(db.child(String.valueOf(rS.id)));

//                while (check.x >= g.tilesX || check.y >= g.tilesY || check.x <= 0 || check.y <= 0) {
//                    check = rS.checkMovement();
//                    //rS.reset(new Point(9,11));  //This is where deaths will be called.
//                }
                getdir.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                      rS.queuedDirection = (String) dataSnapshot.getValue();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            rS.update(rS.queuedDirection);  //All clear.  Move as normal.

            //System.out.println(String.valueOf(db.child(String.valueOf(rS.id))));
            db.child(String.valueOf(rS.id)).setValue(rS.queuedDirection);

        }


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
                counter++;
                //reducing the number of apples
                if(counter == 6 || counter == 7 || counter == 8){
                    idealAppleCount--;
                }
            }
        }

        for(RelativeSnake rS : snakes) {
            ArrayList<Point> segmentTiles = RelativeSnakeBuilder.getSegmentCoords(rS);
            for(Point p:segmentTiles) {
                try {
                    //System.out.println("Segment: " + p.toString());
                    snakeTiles.add(p);
                    g.mapgrid[p.x][p.y] = g.SNAKE_ID;
                } catch (ArrayIndexOutOfBoundsException oob) {
                    continue;
                }
            }
        }



        //Debugging code to make apples "decay" over time so I can see their spawn behavior.
//        if(rnd.nextInt(25) == 1) {
//            /*Point p = appleTiles.get(rnd.nextInt(appleTiles.size()));
//            g.mapgrid[p.x][p.y] = 0;*/
//            for(RelativeSnake rS : snakes) {
//                rS.queuedDirection = getRandDirection(rS.queuedDirection);
//            }
//        }

        if(elapsed == 0) elapsed = 1;
        //System.out.println("FPS: " + (1000 / elapsed));
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

    private String getRandDirection(String current){
        String inverse;
        switch (current){
            case "Up":
                inverse = "Down";
                break;
            case "Down":
                inverse = "Up";
                break;
            case "Left":
                inverse = "Right";
                break;
            case "Right":
                inverse = "Left";
                break;
            default:
                inverse = "NA";
        }
        String rand = getRandDirection();
        while(rand.equals(inverse)){
            //System.out.println(rand + " was " + inverse);
            rand = getRandDirection();
        }
        //System.out.println(rand + " was NOT " + inverse);
        return rand;
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
            long elapsed = System.currentTimeMillis() - lastTimeMillis;
            lastTimeMillis = System.currentTimeMillis();
            update(elapsed);
            render();
        }
    }

}
