package com.example.kenny.game;

import android.graphics.Rect;

/**
 * Created by Kenny on 10/3/2015.
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected int dy;
    protected int dx;
    protected int width;
    protected int height;

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }

    public int getHeight() {
        return height;
    }

    public void setX(int x) {
        this.x = x;
    }
    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setY(int y) {
        this.y = y;
    }
    public Rect getRectangle()         ///For Collisions
    {
        return new Rect(x,y,x+width,y+height);
    }

}
