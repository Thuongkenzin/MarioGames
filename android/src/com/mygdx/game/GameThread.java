package com.mygdx.game;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

public class GameThread extends Thread {

    private boolean running;
    private GameSurface gameSurface;
    private SurfaceHolder surfaceHolder;

    public GameThread(GameSurface gameSurface, SurfaceHolder surfaceHolder)  {
        this.gameSurface= gameSurface;
        this.surfaceHolder= surfaceHolder;
    }

    @Override
    public void run()  {
        long startTime = System.nanoTime();
        while(running)  {
            Canvas canvas= null;
            try {
                // Lock canvas object
                canvas = this.surfaceHolder.lockCanvas();
                // Đồng bộ hóa.
                synchronized (canvas)  {
                    this.gameSurface.update();
                    this.gameSurface.draw(canvas);
                }
            }catch(Exception e)  {

            } finally {
                if(canvas!= null)  {
                    // Unlock canvas
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime() ;
            // Time to update display game
            // (Đổi từ Nanosecond ra milisecond).
            long waitTime = (now - startTime)/1000000;
            if(waitTime < 20)  {
                waitTime= 20; // Millisecond.
            }
            System.out.print(" Wait Time="+ waitTime);
            Log.e("XY", this.gameSurface.chibi1.x + " " + this.gameSurface.chibi1.y);
            Log.e("OLD XY", this.gameSurface.chibi1.oldX + " " + this.gameSurface.chibi1.oldY);
            Log.e("Moving", this.gameSurface.chibi1.movingVectorX + " " + this.gameSurface.chibi1.movingVectorY);
            try {
                this.sleep(waitTime);
            } catch(InterruptedException e)  {

            }
            startTime = System.nanoTime();
            System.out.print(".");
        }
    }

    public void setRunning(boolean running)  {
        this.running= running;
    }
}