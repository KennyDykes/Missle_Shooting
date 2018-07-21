package com.example.kenny.game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Kenny on 10/2/2015.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
   public static final int WIDTH=856;
   public static final int HEIGHT=480;
   public static final int MOVESPEED=-5;
   private long puffStart,missileStart;
   private MainThread thread;
   private Background bg;
   private Player player;
   private ArrayList<SmokePuff> puff;
   private ArrayList<Missiles> missiles;
   private ArrayList<TopBorder> tborder;
   private ArrayList<BotBorder> bborder;
   private Random rand=new Random();
   private int maxBorderHeight;
   private int minBorderHeight;
   private boolean topDown=true;
   private boolean botDown=true;

   private int progress=20;



   public  GamePanel(Context context)
    {
        super(context);

        //Context is added to the surface holder to handle events
        getHolder().addCallback(this);

        thread=new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
    boolean retry=true;
        while(retry)
        {
            try{
                thread.setRunning(false); //Try stopping thread by setting boolean to false
                thread.join();

            }catch(Exception e){}
            retry =false;

        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        bg=new Background(BitmapFactory.decodeResource(getResources(), R.drawable.grassbg1));
        player=new Player(BitmapFactory.decodeResource(getResources(),R.drawable.helicopter),65,25,3);

        puff=new ArrayList<SmokePuff>();
        missiles=new ArrayList<Missiles>();
        bborder=new ArrayList<BotBorder>();
        tborder=new ArrayList<TopBorder>();

        missileStart=System.nanoTime();
        puffStart=System.nanoTime();                 //Timer for smoke puffs
        thread.setRunning(true);
        thread.start();

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if(event.getAction()==MotionEvent.ACTION_DOWN)
        {
            if(!player.isPlaying()) {
                player.setPlaying(true);
            }
            else{
                player.setUp(true);
            }
            return true;
        }
        if(event.getAction()==MotionEvent.ACTION_UP)
        {
          player.setUp(false);
          return true;
        }
        return super.onTouchEvent(event);
    }
    public boolean collision(GameObject a,GameObject b)
    {
        if(Rect.intersects(a.getRectangle(),b.getRectangle()))
        {
            return true;
        }
        return false;
    }
    public void update()
    {
        if(player.isPlaying()) {
            bg.update();
            player.update();


            maxBorderHeight=30+ player.getScore()/progress;
            if(maxBorderHeight> HEIGHT/4) maxBorderHeight=HEIGHT/4;

            minBorderHeight= 5+ player.getScore()/progress;

            this.updateTopBorder();

            this.updateBottomBorder();

           long  missileElapsed=(System.nanoTime()-missileStart)/1000000;
            if(missileElapsed>(2000-player.getScore()/4))
            {
             //   System.out.print("Missile launched");
                if(missiles.size()==0)
                {
                missiles.add(new Missiles (BitmapFactory.decodeResource(getResources(), R.drawable.missile)
                         , WIDTH + 10, HEIGHT / 2,45,15, player.getScore(), 13));
                }
                else{
                missiles.add(new Missiles (BitmapFactory.decodeResource(getResources(), R.drawable.missile)
                            , WIDTH +10 , (int)rand.nextDouble()*(HEIGHT)/2,45,15, player.getScore(), 13));
                }
                missileStart=System.nanoTime();

                for(int i=0;i<missiles.size();i++)
                {
                    missiles.get(i).update();

                    if(collision(missiles.get(i),player))
                    {
                        missiles.remove(i);
                        player.setPlaying(false);
                        break;
                    }
                    if(missiles.get(i).getX()<-100)
                    {
                        missiles.remove(i);
                        break;
                    }
                }

            }
            long elapsed=(System.nanoTime()-puffStart)/1000000;
            if(elapsed>120)
            {
                puff.add(new SmokePuff(player.getX()+10,player.getY()));
                puffStart=System.nanoTime(); ///restarts timer
            }

            for(int i=0;i<puff.size();i++)
            {
               puff.get(i).update();
                if(puff.get(i).getX()<-10)  ///If puff is off the screen
                {
                    puff.remove(i);
                }
            }
        }
    }

    @Override
    public void draw(Canvas canvas) {
        final float scaleX=getWidth()/(WIDTH * 1f);
        final float scaleY=getHeight()/(HEIGHT * 1f);
        if(canvas!=null) {
            final int state=canvas.save();
            canvas.scale(scaleX,scaleY);
            bg.draw(canvas);
            player.draw(canvas);

            for(SmokePuff it:puff)
            {
                it.draw(canvas);
            }
            for(Missiles shoot:missiles)
            {
                shoot.draw(canvas);
            }
            canvas.restoreToCount(state);
        }

    }
    public void updateBottomBorder()
    {
      if(player.getScore() % 50==0)
      {
          tborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),tborder.get(tborder.size()-1).getX()+20,
                  0, (int)(rand.nextDouble()*(maxBorderHeight))+1));
      }
        for(int i=0;i<tborder.size();i++)
        {
            tborder.get(i).updste();

            if(tborder.get(i).getX()<-20)
            {
                tborder.remove(i);
                if(tborder.get(tborder.size()-1).getHeight()>=maxBorderHeight)
                {
                    topDown=false;
                }
                if(tborder.get(tborder.size()-1).getHeight()<=minBorderHeight)
                {
                    topDown=true;
                }
                if(topDown)
                {
                    tborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            tborder.get(tborder.size()-1).getX()+20, 0,
                            tborder.get(tborder.size()-1).getHeight()+1));
                }
                else
                {
                    tborder.add(new TopBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),
                            tborder.get(tborder.size()-1).getX()+20, 0,
                            tborder.get(tborder.size()-1).getHeight()-1));
                }
            }

        }
    }

    public void updateTopBorder()
    {
      if(player.getScore() % 40==0)
      {
          bborder.add(new BotBorder(BitmapFactory.decodeResource(getResources(),R.drawable.brick),bborder.get(bborder.size()-1).getX()+20,
                   (int)(rand.nextDouble()*(maxBorderHeight))+(HEIGHT-maxBorderHeight)));

      }
    }
}
