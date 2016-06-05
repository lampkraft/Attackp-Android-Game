package game;

import android.graphics.Canvas;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 *  Contains all functionality needed for a positioned button
 */
public class Button {

    private int state;
    Sprite sprite;
    int x, y;

    /**
     *  Constructor
     *  @param sprite sprite of the button
     *  @param x x position of the button
     *  @param y y position of the button
     */
    public Button(Sprite sprite, int x, int y){
        this.sprite = sprite;
        this.x = x;
        this.y = y;
    }

    /**
     *  Calls the draw function in sprite
     *  @param c canvas to draw on
     */
    public void draw(Canvas c){
        sprite.draw(c, x, y);
    }

    /**
     *  Checks if touched the buttons collision box
     *  @param touchX x position of the touch
     *  @param touchY y position of the touch
     */
    public boolean onTouch(float touchX, float touchY, int frame){
        if(touchX > x && touchX < x + sprite.width && touchY > y && touchY < y + sprite.height){
            return true;
        }
        return false;
    }

    /**
     *  Changes the sprites image
     *  @param frame what frame to change to
     */
    public void onHover(int frame){
        sprite.direction = frame;
    }

    /**
     * Reset the sprites frame value
     */
    public void onLeave(){
        sprite.direction = 0;
    }

    /**
     * Sets the position of the button object
     * @param x x position
     * @param y y position
     */
    public void setPosition(int x, int y){
        this.x = x;
        this.y = y;
    }

    /**
     * Set the state to an "extrabutton" and change the frame of the sprite
     *
     * State 1 - gives more points
     * State 2 - gives more time
     * State 3 - gives less points
     * State 4 - gives less time
     *
     * @param s state to set
     */
    public void setState(int s){
        state = s;
        sprite.setDirection(s);
    }

    /**
     * Returns the state of the button
     * @return state
     */
    public int getState(){
        return state;
    }

    /**
     * Returns the x position of the button
     * @return x
     */
    public int getX(){
        return x;
    }

    /**
     * Returns the y position of the button
     * @return y
     */
    public int getY(){
        return y;
    }

    /**
     * Returns the sprite of the button
     * @return sprite
     */
    public Sprite getSprite(){
        return sprite;
    }

    /**
     * Resets the image frame and state of the button
     */
    public void restoreDirection(){
        state = 0;
        sprite.direction = 0;
    }
}
