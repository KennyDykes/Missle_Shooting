package com.example.kenny.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;


/**
 * Created by Kenny on 10/3/2015.
 */
public class Player extends GameObject{
    private Bitmap image;
    private int score;
    private boolean playing;
    private boolean up;
    private long startTime;
    Animation animate=new Animation();
    Player(Bitmap bit,int w, int h,int numFrames)
    {
        x=100;
        y=GamePanel.HEIGHT/2;
        dy=0;
        score=0;
        height =h;
        width=w;
        Bitmap [] img=new Bitmap[numFrames];
        image=bit;
    for(int i=0;i<img.length;i++)
    {
        img[i]=Bitmap.createBitmap(image,i*width,0,width,height);
    }
        animate.setFrame(img);
        animate.setDelay(10);
        startTime=System.nanoTime();
    }
    public void setUp(boolean trigger)
    {up=trigger;}
    public void update()
    {
        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>100)
        {
            score++;
            startTime=System.nanoTime();
        }
        animate.update();
        if(up)
        {
           dy-=1.1;
        }else
        {
            dy+=1.1;
        }
        if(dy>14)dy=14; //maximum

        if(dy<-14)dy=-14; //minimum

        y += dy*2;

        dy=0;
    }
    public void draw(Canvas canvas)
   {
    canvas.drawBitmap(animate.getImage(),x,y,null);
   }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setPlaying(boolean playing) {
        this.playing = playing;
    }

    public boolean isPlaying() {
        return playing;
    }
    public void resetAccelerator()
    { dy=0;}
    public void resetScore()
    { score=0;}
}
