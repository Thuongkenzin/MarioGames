package com.mygdx.game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.view.View;

import java.util.ArrayList;

public class Level6 extends LevelBase {
    public Level6(View gameSurface, Bitmap chibiBitmap){
        gameSurface.setBackgroundResource(R.drawable.forest3);
        this.chibi = new ChibiAnimation(gameSurface, chibiBitmap, 520, 570);
        monster = new Monster(BitmapFactory.decodeResource(gameSurface.getResources(), R.drawable.monster5), 520, 270);
        monsterCount = 1;
        listPoint = new ArrayList<Point>();
        int x = 550;
        int y = 450;
        for (int i = 0; i < 3; i++) {
            listPoint.add(new Point(x, y));
            y -=100;
        }
        this.misssion = "Di chuyển tới vị trí chỉ định để tiêu diệt quái vật";
    }
}