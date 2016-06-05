package game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;


import lampkraft.attackp.R;

import static android.content.SharedPreferences.*;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * Activity used in game mode
 */
public class GameActivity extends Activity implements OnTouchListener{

    MySurfaceView v;
    GameTimer gameTimer;
    Bitmap[] bitmaps = new Bitmap[2];
    Sprite[] sprites = new Sprite[5];
    Button[] btn_game = new Button[5];
    Button btnPressed;
    Paint textPaint, timeBarPaint;
    String drawZeros;
    int score = 0, highscore = 0, lastScore = 0;

    SharedPreferences prefs;
    Editor editor;

    /**
     * Called when the activity is created.
     * Sets up the main game stage and its resources.
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //setting preferences
        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        highscore = prefs.getInt("highscore", 0);
        editor = prefs.edit();

        Typeface tf = Typeface.createFromAsset(getAssets(), "Titania.ttf");

        textPaint = new Paint();
        textPaint.setColor(Color.argb(255, 254, 241, 175));
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(60);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTypeface(tf);
        textPaint.setAntiAlias(true);

        timeBarPaint = new Paint();
        timeBarPaint.setColor(Color.argb(255, 65, 65, 255));
        timeBarPaint.setStyle(Paint.Style.FILL);

        v = new MySurfaceView(this);
        v.setOnTouchListener(this);
        setContentView(v);

        bitmaps[0] = BitmapFactory.decodeResource(getResources(), R.drawable.btn_main_small);
        bitmaps[1] = BitmapFactory.decodeResource(getResources(), R.drawable.attackp_btn_extras);

        sprites[0] = new Sprite(v, bitmaps[0], 2);
        sprites[1] = new Sprite(v, bitmaps[1], 5);
        sprites[2] = new Sprite(v, bitmaps[1], 5);
        sprites[3] = new Sprite(v, bitmaps[1], 5);
        sprites[4] = new Sprite(v, bitmaps[1], 5);

        btn_game[0] = new MainButton(sprites[0], 0, 0);
        btn_game[1] = new ExtraButton(sprites[1], 0, 0);
        btn_game[2] = new ExtraButton(sprites[2], 0, 0);
        btn_game[3] = new ExtraButton(sprites[3], 0, 0);
        btn_game[4] = new ExtraButton(sprites[4], 0, 0);

        gameTimer = new GameTimer(btn_game[1], btn_game[2], btn_game[3], btn_game[4]);

    }

    /**
     * Called when the activity is resumed
     * Resumes activity
     */
    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    /**
     * Called when the activity is paused
     * Pauses the activity
     */
    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    /**
     * Called when the activity is being touched at.
     * Checks the screen where the user has pressed and does things accordingly.
     *
     * @param v view to use
     * @param e event object
     * @return true
     */
    @Override
    public boolean onTouch(View v, MotionEvent e) {
        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN:
                for (Button b : btn_game){
                    if(b.onTouch(e.getX(), e.getY(), 1)) btnPressed = b;
                }
                if(btnPressed == btn_game[0] && btnPressed.onTouch(e.getX(), e.getY(), 1)){
                    score += 1;
                    btnPressed.onHover(1);
                    gameTimer.addTime((float) 0.04);
                } else if(btnPressed.onTouch(e.getX(), e.getY(), 1) && (btnPressed == btn_game[1] || btnPressed == btn_game[2] ||
                          btnPressed == btn_game[3] || btnPressed == btn_game[4])){

                   if(btnPressed.getState() == 1){// more score
                       score += 50;
                   } else if(btnPressed.getState() == 2){// more time
                       gameTimer.addTime(3);
                   } else if(btnPressed.getState() == 3){// less time
                       gameTimer.subTime(5);
                   } else if(btnPressed.getState() == 4){// less score
                       score = score/2;
                   }

                   btnPressed.restoreDirection();
                }
                break;

            case MotionEvent.ACTION_UP:
                btnPressed.onLeave();
                break;
        }

        return true;
    }

    /**
     * Inner class extending SurfaceView to create the canvas and draw everything on screen
     */
    public class MySurfaceView extends SurfaceView implements Runnable{

        Thread t = null;
        SurfaceHolder holder;
        boolean isItOK = false;
        Canvas c;

        /**
         * Constructor
         * Calls parent constructor and sets the surfaceholder
         * @param context
         */
        public MySurfaceView(Context context) {
            super(context);
            holder = getHolder();
        }

        /**
         * Run method. Draws the surface in a thread
         */
        public void run() {

            while(isItOK){
                if(!holder.getSurface().isValid()){
                    continue;
                }

                c = holder.lockCanvas();

                this.draw();

                holder.unlockCanvasAndPost(c);
            }
        }

        /**
         * Draws everything on the surface
         */
        public void draw(){

            c.drawARGB(255, 28, 181, 78);//background

            btn_game[0].setPosition(c.getWidth()/2-(sprites[0].getWidth()/2), c.getHeight()/2-(sprites[0].getHeight()/2));
            btn_game[1].setPosition(btn_game[0].getX() - btn_game[1].getSprite().getWidth()-20, btn_game[0].getY() - btn_game[1].getSprite().getHeight());
            btn_game[2].setPosition(btn_game[0].getX() + btn_game[0].getSprite().getWidth()+20, btn_game[0].getY() - btn_game[2].getSprite().getHeight());
            btn_game[3].setPosition(btn_game[0].getX() - btn_game[3].getSprite().getWidth()-20, btn_game[0].getY() + btn_game[0].getSprite().getHeight());
            btn_game[4].setPosition(btn_game[0].getX() + btn_game[0].getSprite().getWidth()+20, btn_game[0].getY() + btn_game[0].getSprite().getHeight());

            //For a different resolution
            /*btn_game[1].setPosition(btn_game[0].getX() - btn_game[1].getSprite().getWidth()-20, btn_game[0].getY());
            btn_game[2].setPosition(btn_game[0].getX() + btn_game[0].getSprite().getWidth()+20, btn_game[0].getY());
            btn_game[3].setPosition(btn_game[0].getX() - btn_game[3].getSprite().getWidth()-20, btn_game[0].getY() + btn_game[0].getSprite().getHeight() - btn_game[3].getSprite().getHeight());
            btn_game[4].setPosition(btn_game[0].getX() + btn_game[0].getSprite().getWidth()+20, btn_game[0].getY() + btn_game[0].getSprite().getHeight() - btn_game[4].getSprite().getHeight());*/

            for (Button b : btn_game) b.draw(c);

            c.drawRect(0, 0, (gameTimer.getTime()/gameTimer.getMaxTime()) * c.getWidth(), 45, timeBarPaint);
            if(score < 10) drawZeros = "00000";
            else if(score < 100) drawZeros = "0000";
            else if(score < 1000) drawZeros = "000";
            else if(score < 10000) drawZeros = "00";
            else if(score < 100000) drawZeros = "0";
            c.drawText(drawZeros + score, c.getWidth() / 2, c.getHeight() / 2 + 10, textPaint);

            //game end
            if(gameTimer.getEnd()){
                if(score > highscore){
                    editor.putInt("lastscore", prefs.getInt("highscore", 0));
                    editor.putInt("highscore", score);
                    editor.putBoolean("newHighscore", true);
                    editor.commit();
                    highscore = prefs.getInt("highscore", 0);
                } else{
                    editor.putInt("lastscore", score);
                    editor.putBoolean("newHighscore", false);
                    editor.commit();
                }

                score = 0;
                gameTimer.setEnd(false);
                Intent highscore = new Intent("com.attackp.SHOWHIGHSCORE");
                startActivity(highscore);
            }
        }

        /**
         * Called when activity is paused
         * Pauses game
         */
        public void pause() {
            isItOK = false;
            while(true){
                try{
                    t.join();
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        /**
         * Called when activity is resumed
         * Restarts the game session so that a user cannot go back to game
         * after leaving the activity
         */
        public void resume() {
            isItOK = true;
            highscore = prefs.getInt("highscore", 0);
            t = new Thread(this);
            t.start();//start thread
        }


    }
}
