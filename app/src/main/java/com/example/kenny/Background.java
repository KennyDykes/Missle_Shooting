package com.example.kenny.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kenny on 10/2/2015.
 */
public class Background {
    private Bitmap bitmap;
    private int x, y,dx ;

    Background (Bitmap back)
    {
        this.bitmap=back;
        this.dx=GamePanel.MOVESPEED;
    }
    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(bitmap,x,y,null);
        if(x<0)
        {
         canvas.drawBitmap(bitmap,x+GamePanel.WIDTH,y,null);
        }
    }
    public void update()
    {
        x+=dx;
        if(x<-GamePanel.WIDTH+5)
        {
            x=0;
        }

    }
    /*
   public void setVector(int dx)
   {this.dx=dx;}*/


}
