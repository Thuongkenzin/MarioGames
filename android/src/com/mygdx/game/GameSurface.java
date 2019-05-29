package com.mygdx.game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    private GameThread gameThread;
    public ChibiCharacter chibi1;
    private int step, type;
    public GameSurface(Context context, int step, int type)  {
        super(context);
        this.step = step;
        this.type = type;
        this.setFocusable(true);
        //Set event game control
        this.getHolder().addCallback(this);
    }

    public void update()  {
        this.chibi1.update();
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        this.chibi1.draw(canvas);
    }

    //Execute method interface SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.image);
        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,100,100);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Excute method interface SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    //Execute interface SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);
                // Waiting for GameThread finish.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }
            retry= true;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            switch (type) {
                case 0:
                    chibi1.setMovingVector(step + this.chibi1.x, 0);
                    break;
                case 1:
                    chibi1.setMovingVector(-(step + this.chibi1.x), 0);
                    break;
            }
            return true;
        }
        return false;
    }

    public void setMotion(int step, int type) {
        switch (type) {
            case 0:
                chibi1.setMovingVector(step, 0);
                break;
            case 1:
                chibi1.setMovingVector(-step, 0);
                break;
        }
    }

    public void pause() {
    }

    public void resume() {
    }
}
