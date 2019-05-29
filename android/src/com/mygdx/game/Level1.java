package com.mygdx.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

public class Level1 extends LevelBase {
    public Level1(View gameSurface, Bitmap chibiBitmap){
        gameSurface.setBackgroundResource(R.drawable.forest);
        this.chibi = new ChibiAnimation(gameSurface, chibiBitmap, 20, 270);
        monster = new Monster(BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.monster3), 120, 270);
        monsterCount = 1;
        listPoint = new ArrayList<Point>();
        int x = 150;
        int y = 350;
        for (int i = 0; i < 1; i++) {
            listPoint.add(new Point(x, y));
            x +=100;
        }
        this.misssion = "Di chuyển tới vị trí chỉ định để tiêu diệt quái vật";
    }
}
