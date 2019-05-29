package com.mygdx.game;

import android.graphics.Bitmap;

public class Monster {
    public Bitmap bitmap;
    public int x, y;
    public Monster(Bitmap bitmap, int x, int y){
        this.bitmap = bitmap;
        this.x = x;
        this.y = y;
    }
}
