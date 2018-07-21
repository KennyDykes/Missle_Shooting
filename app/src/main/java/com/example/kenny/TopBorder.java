package com.example.kenny.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kenny on 10/15/2015.
 */
public class TopBorder extends GameObject {
    private Bitmap image;
    public TopBorder(Bitmap map,int x, int y, int h)
    {
     height=h;
     width=20;

     this.x=x;
     this.y=y;

     dx=GamePanel.MOVESPEED;

     image=Bitmap.createBitmap(map,0,0,width, height);

    }

    public void updste()
    {
        x+=dx;
    }
    public void draw(Canvas canvas)
    {
        try{
            canvas.drawBitmap(image,x,y,null);
        }catch(Exception ed)
        {

        }
    }

}
