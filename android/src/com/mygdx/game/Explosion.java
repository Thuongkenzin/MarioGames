package com.mygdx.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View;

public class Explosion extends GameObject {
    private int rowIndex = 0;
    private int colIndex = -1;
    private boolean finish = false;
    private View gameView;
    public Explosion(View v, Bitmap img, int x, int y){
        super(img, 5, 5, x, y);
        this.gameView = v;
    }

    public void update(){
        this.colIndex++;
        if(this.colIndex >= this.colCount){
            colIndex = 0;
            rowIndex++;
            if(rowIndex >= rowCount){
                this.finish = true;
            }
        }
    }

    public void draw(Canvas canvas){
        update();
        if(!finish){
            Bitmap bitmap = createSubImageAt(rowIndex, colIndex);
            canvas.drawBitmap(bitmap, x, y, null);
        }
    }

    public boolean isFinish(){
        return finish;
    }
}
