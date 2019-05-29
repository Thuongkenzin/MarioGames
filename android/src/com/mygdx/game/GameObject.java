package com.mygdx.game;

import android.graphics.Bitmap;

public abstract class GameObject {

    protected Bitmap image;

    protected final int rowCount;
    protected final int colCount;

    protected final int WIDTH;
    protected final int HEIGHT;
    protected final int width;
    protected final int height;
    protected int x;
    protected int y;

    public GameObject(Bitmap image, int rowCount, int colCount, int x, int y)  {
        this.image = image;
        this.rowCount= rowCount;
        this.colCount= colCount;
        this.x= x;
        this.y= y;
        //Image size
        this.WIDTH = image.getWidth();
        this.HEIGHT = image.getHeight();
        //Character size
        this.width = this.WIDTH/ colCount;
        this.height= this.HEIGHT/ rowCount;
    }


    protected Bitmap createSubImageAt(int row, int col)  {
        // create character sub image
        Bitmap subImage = Bitmap.createBitmap(image, col* width, row* height ,width,height);
        return subImage;
    }

    public int getX()  {
        return this.x;
    }

    public int getY()  {
        return this.y;
    }

    public void setXY(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}
