package com.mygdx.game;
import android.graphics.Bitmap;
import android.graphics.Canvas;

public class ChibiCharacter extends GameObject {

    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;
    private static final int STOP = 4;

    // Image state using
    private int rowUsing = STOP;
    private int colUsing;

    //Array image state character
    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;
    private Bitmap[] stopped;

    //Speed (pixel/milisecond).
    public static final float VELOCITY = 0.1f;
    public int movingVectorX = 0;
    public int movingVectorY = 0;
    public int oldX, oldY;

    private long lastDrawNanoTime =-1;
    private GameSurface gameSurface;

    public ChibiCharacter(GameSurface gameSurface, Bitmap image, int x, int y) {
        super(image, 4, 3, x, y);
        oldX = x;
        oldY = y;
        this.gameSurface= gameSurface;
        this.topToBottoms = new Bitmap[colCount];
        this.rightToLefts = new Bitmap[colCount];
        this.leftToRights = new Bitmap[colCount];
        this.bottomToTops = new Bitmap[colCount];

        for(int col = 0; col< this.colCount; col++ ) {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
        this.stopped = new Bitmap[1];
        this.stopped[0] = this.leftToRights[0];
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
        // Current time theo nano second.
        long now = System.nanoTime();

        // No draw any time
        if(lastDrawNanoTime==-1) {
            lastDrawNanoTime= now;
        }

        // Change nano second to mili second (1 nanosecond = 1000000 millisecond).
        int deltaTime = (int) ((now - lastDrawNanoTime)/ 1000000 );

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
            this.movingVectorX = - this.movingVectorX;
        } else if(this.x > this.gameSurface.getWidth() -width)  {
            this.x= this.gameSurface.getWidth()-width;
            this.movingVectorX = - this.movingVectorX;
        }

        if(this.y < 0 )  {
            this.y = 0;
            this.movingVectorY = - this.movingVectorY;
        } else if(this.y > this.gameSurface.getHeight()- height)  {
            this.y= this.gameSurface.getHeight()- height;
            this.movingVectorY = - this.movingVectorY ;
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
        Bitmap bitmap = this.getCurrentMoveBitmap();
        canvas.drawBitmap(bitmap,x, y, null);

        // Time last draw(Nano giây).
        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)  {
        this.movingVectorX= movingVectorX;
        this.movingVectorY = movingVectorY;
    }
}