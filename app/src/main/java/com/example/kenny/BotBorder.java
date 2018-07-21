package com.example.kenny.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kenny on 10/15/2015.
 */
public class BotBorder extends GameObject{
    private Bitmap bottom;
    BotBorder(Bitmap res,int x,int y)
    {
        height=200;
        width=20;

        this.x=x;
        this.y=y;

        bottom=Bitmap.createBitmap(res,0,0,width,height);


    }

    public void update()
    {
        x+=dx;
    }
    public void draw(Canvas canvas)
    {
        try
        {
            canvas.drawBitmap(bottom,x,y,null);
        }catch(Exception e){}
    }
}
