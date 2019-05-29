package com.mygdx.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class ChibiAnimation extends GameObject{

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;
    private static final int STOP = 4;
    //Test new chibi sheet
    private static final int ROW_IDE = 0;
    private static final int ROW_JUMP_LEFT = 1;
    private static final int ROW_JUMP_RIGHT = 2;
    private static final int ROW_RUN = 3;
    private static final int ROW_WALK_LEFT = 4;
    private static final int ROW_WALK_RIGHT = 5;

    // Image state using
    private int rowUsing = STOP;
    private int colUsing;

    //Array image state character
    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;
    private Bitmap[] stopped;

    // Test new chibi
    private Bitmap[] jumpLeft;
    private Bitmap[] jumpRight;
    private Bitmap[] runLeft;

    //Speed (pixel/milisecond).
    public static final float VELOCITY = 0.1f;
    public int movingVectorX = 0;
    public int movingVectorY = 0;
    public int oldX, oldY;

    private long lastDrawNanoTime =-1;
    private View gameSurface;

    public ChibiAnimation(View gameSurface, Bitmap image, int x, int y) {
        super(image, 6, 7, x, y);
        oldX = x;
        oldY = y;
        this.gameSurface= gameSurface;
        this.stopped = new Bitmap[colCount];
        this.topToBottoms = new Bitmap[colCount];
        this.rightToLefts = new Bitmap[colCount];
        this.leftToRights = new Bitmap[colCount];
        this.bottomToTops = new Bitmap[colCount];

        this.jumpRight = new Bitmap[colCount];
        this.jumpLeft = new Bitmap[colCount];
        this.runLeft = new Bitmap[colCount];
        for(int col = 0; col< this.colCount; col++ ) {
            this.stopped[col] = this.createSubImageAt(ROW_IDE, col);
            this.jumpLeft[col] = this.createSubImageAt(ROW_JUMP_LEFT, col);
            this.jumpRight[col]  = this.createSubImageAt(ROW_JUMP_RIGHT, col);
            this.runLeft[col] = this.createSubImageAt(ROW_RUN, col);
            this.leftToRights[col]  = this.createSubImageAt(ROW_WALK_LEFT, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_WALK_RIGHT, col);
        }
        topToBottoms = leftToRights;
        bottomToTops = rightToLefts;
//        for(int col = 0; col< this.colCount; col++ ) {
//            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
//            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
//            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
//            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
//        }
//        this.stopped = new Bitmap[1];
//        this.stopped[0] = this.leftToRights[0];
    }

    public Bitmap[] getMoveBitmaps()  {
        switch (rowUsing)  {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            case STOP:
                return this.stopped;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()  {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        if(rowUsing == STOP)
            return bitmaps[0];
        else
            return bitmaps[this.colUsing];
    }

    public void update()  {
        this.colUsing++;
        if(colUsing >= this.colCount)  {
            this.colUsing =0;
        }

        // Distance move (fixel).
        float distance = 5;
        double movingVectorLength = Math.sqrt(movingVectorX* movingVectorX + movingVectorY*movingVectorY);

        // New position character
        this.x = x +  (int)(distance* movingVectorX / movingVectorLength);
        this.y = y +  (int)(distance* movingVectorY / movingVectorLength);

        if(Math.abs(x - oldX) == Math.abs(movingVectorX) && Math.abs(y - oldY) == Math.abs(movingVectorY))
            setMovingVector(0,0);

        // Change direction.
        if(this.x < 0 )  {
            this.x = 0;
            this.movingVectorX = 0;
        } else if(this.x > this.gameSurface.getWidth() -width)  {
            this.x= this.gameSurface.getWidth()-width;
            this.movingVectorX = 0;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = 0;
        } else if(this.y > this.gameSurface.getHeight()- height)  {
            this.y= this.gameSurface.getHeight()- height;
            this.movingVectorY = 0 ;
        }


        // Calculate rowUsing.
        if( movingVectorX > 0 )  {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        } else {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }else  {
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }
        if(movingVectorX == 0 && movingVectorY == 0)
            this.rowUsing = STOP;
    }

    public void draw(Canvas canvas)  {
        update();
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);

        // Time last draw(Nano giây).
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }
    public void resetStartXY(){
        oldX = x;
        oldY = y;
    }
    public int getMovingX(){
        return movingVectorX;
    }
    public int getMovingY(){
        return movingVectorY;
    }
    public int getState(){
        return rowUsing;
    }
}
