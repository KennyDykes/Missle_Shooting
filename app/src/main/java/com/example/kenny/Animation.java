package com.example.kenny.game;

import android.graphics.Bitmap;

/**
 * Created by Kenny on 10/4/2015.
 */
public class Animation {

    private Bitmap[] frames;
    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean playedOnce;
    public void setFrame(Bitmap[] frames)
    {
      this.frames=frames;
      currentFrame=0;
      startTime=System.nanoTime();
    }
    public void setDelay(long d)   { delay=d; }
    public void setFrame(int f)   { currentFrame=f; }
    public void update()
    {
        long elapsed=(System.nanoTime()-startTime)/1000000;
        if(elapsed>delay)
        {
            currentFrame++;
            startTime=System.nanoTime();
        }
        if(currentFrame==frames.length)
        {
            currentFrame=0;
            playedOnce=true;
        }
    }
  public Bitmap getImage()
 {
     return frames[currentFrame];
 }

    public boolean isPlayedOnce() {
        return playedOnce;
    }

    public int getCurrentFrame() {
        return currentFrame;
    }
}
