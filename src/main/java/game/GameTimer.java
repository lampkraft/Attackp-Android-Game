package game;

import java.util.Random;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * Handling the random events in the game through a timer thread
 */
public class GameTimer implements Runnable{
    float time, maxTime, changedTimer;
    Thread t = null;
    boolean isItOK = false, isChanged = false, end = false;
    Random rand;
    Button btn_chosen;

    Button[] btn_game = new Button[4];

    /**
     * Constructor
     * @param a a button
     * @param b a button
     * @param c a button
     * @param d a button
     */
    public GameTimer(Button a, Button b, Button c, Button d) {
        maxTime = 10;
        time = maxTime;
        changedTimer = 5;
        rand = new Random();

        btn_game[0] = a;
        btn_game[1] = b;
        btn_game[2] = c;
        btn_game[3] = d;

        resume();
    }

    /**
     * Returns the current time
     * @return time
     */
    public float getTime(){
        return time;
    }

    /**
     * Returns the maximum value of time
     * @return the value of maximum time
     */
    public float getMaxTime(){
        return maxTime;
    }

    /**
     * Subtracts time
     * @param amount amount to be subtracted
     */
    public void subTime(float amount){
        time -= amount;
    }

    /**
     * Adds time
     * @param amount amount of time to add
     */
    public void addTime(float amount){
        time += amount;
    }

    /**
     * A threads method to run. Handles some random events like spawning buttons
     * and the timer of the game
     */
    @Override
    public void run() {
        while(isItOK) {
            if(time > 0){
                time -= 0.01;
                    if(!isChanged){
                        btn_chosen = btn_game[rand.nextInt(4)];
                        if(rand.nextInt(10) == 5) {
                            btn_chosen.setState(rand.nextInt(5));
                            changedTimer = (float)0.8;
                            isChanged = true;
                        } else if(time < maxTime/4 && rand.nextInt(5) == 5){
                            btn_chosen.setState(rand.nextInt(5));
                            changedTimer = (float)0.8;
                            isChanged = true;
                        }
                    } else{
                        if(changedTimer > 0){
                            changedTimer -= 0.01;
                        } else{
                            changedTimer = 0;
                            btn_chosen.restoreDirection();
                            isChanged = false;
                        }

                    }

                    //btn_game[rand.nextInt(3)].setState(rand.nextInt(3));

            } else{
                time = maxTime;
                setEnd(true);
            }

            try {
                t.sleep(10);
            } catch (InterruptedException e) {
                //do something
            }
        }

    }

    /**
     * Pauses thread
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
     * Resumes thread
     */
    public void resume() {
        isItOK = true;
        t = new Thread(this);
        t.start();//start thread
    }

    /**
     * Set ending of game to a value
     * @param e true or false
     */
    public void setEnd(boolean e){
        end = e;
    }

    /**
     * Get the end value
     * @return true or false
     */
    public boolean getEnd(){
        return end;
    }
}
