package com.mygdx.game;

import android.graphics.Point;

import java.util.ArrayList;

public abstract class LevelBase {
    protected ChibiAnimation chibi;
    protected Monster monster;
    protected int monsterCount;
    protected String misssion;
    public ArrayList<Point> listPoint;

    public ChibiAnimation getChibi(){
        return chibi;
    };
    public Monster getMonster(){
        return monster;
    }
    public int getMonsterCount(){
        return monsterCount;
    }
    public String getMisssion(){
        return misssion;
    }

    public void setMonsterCount(int count){
        this.monsterCount = count;
    }
}
