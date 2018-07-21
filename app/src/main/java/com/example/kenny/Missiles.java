package com.example.kenny.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Kenny on 10/9/2015.
 */
public class Missiles extends GameObject {
    private int score,speed;
    private Random rand=new Random();
    private Animation animation=new Animation();
    private Bitmap sprite;
    Missiles(Bitmap resource,int x,int y,int w, int h ,int s, int numFrames)
    {
        super.x=x;
        super.y=y;
        width=w;
        height=h;
        score=s;
        speed= 7 + (int)(rand.nextDouble()*score/30);
        if(speed>40)speed=40;
        Bitmap[]images=new Bitmap[numFrames];
        sprite=resource;
        for(int i=0;i<images.length;i++)
        {
          images[i]=Bitmap.createBitmap(sprite,0,i*height,width,height);
        }
        animation.setFrame(images);
        animation.setDelay(100-speed);
    }
    public void update()
    {
           x-=speed;
           animation.update();
    }
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(animation.getImage(), x, y, null);
        } catch (Exception e) {

        }
    }

    @Override
    public int getWidth() {
        return width-10;
    }
}
