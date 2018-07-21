package com.example.kenny.game;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kenny on 10/2/2015.
 */
public class MainThread extends Thread {
    private int fps = 30;
    private double average;
    private SurfaceHolder holder;
    private GamePanel game;
    public static Canvas canvas;
    private boolean running;

    MainThread(SurfaceHolder surface, GamePanel panel) {
     super();
     this.holder=surface;
     this.game=panel;
    }
  public void setRunning(Boolean run)
  {
      running=run;
  }
    @Override
    public void run() {
       long start;
       long timeMillis;
       long waitTime;
       long totalTime=0;
       int frameCount=0;
       long targetTime=1000/fps;

        while(running)
        {
          start=System.nanoTime();
          canvas=null;   //Clears canvas

            try {
                canvas = this.holder.lockCanvas();
                synchronized (holder) {
                    this.game.update();     //Updates
                    this.game.draw(canvas); //Redraws canvas
                }
            }catch(Exception e)
                {

                }
            finally
            {
                    if(canvas!=null)
                    {
                        try{
                            holder.unlockCanvasAndPost(canvas);

                        } catch(Exception e){}
                    }
            }
             timeMillis = System.nanoTime()-start/1000000;
             waitTime=targetTime-timeMillis;
            try{
                this.sleep(waitTime);


            }catch(Exception e)
            {

            }
             totalTime+=System.nanoTime()-start;
             frameCount++;

            if(frameCount==fps)
            {
                average=1000/((totalTime/frameCount)/1000000);
                frameCount=0;
                totalTime=0;
                System.out.println(average);

            }



        }


    }
}
