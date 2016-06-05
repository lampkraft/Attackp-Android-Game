package game;

import game.GameActivity.MySurfaceView;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Lampkraft on 2015-10-15.
 */

/**
 * The sprite handles the image of a game object.
 * It needs a bitmap, size, position and a surface to be drawn to
 */
public class Sprite{

    int height, width, direction;
    int x, y;
    Bitmap b;
    MySurfaceView sv;


    /**
     * Constructor
     * Sets up the sprite
     * @param mySurfaceView surface to draw to
     * @param bitmap image to use
     * @param columns amount of columns to split upp the image in
     *                to make it able to animate between images
     */
    public Sprite(MySurfaceView mySurfaceView, Bitmap bitmap,  int columns) {
        b = bitmap;
        sv = mySurfaceView;
        height = b.getHeight();
        width = b.getWidth() / columns; //two columns
        direction = 0;
    }

    /**
     * Update the sprite if needed before drawing
     */
    public void update(){

    }

    /**
     * Draws the sprite on a canvas object
     * @param c canvas to use
     * @param x x position
     * @param y y position
     */
    public void draw(Canvas c, int x, int y){
        update();
        this.x = x;
        this.y = y;
        int srcX = direction * width;
        Rect src = new Rect(srcX, 0, srcX + width, height);
        Rect dst = new Rect(x, y, x + width, y + height);
        c.drawBitmap(b, src, dst, null);
    }

    /**
     * Set the direction of the sprite to change image
     * @param d value of direction
     */
    public void setDirection(int d){
        direction = d;
    }

    /**
     * Returns the width of the sprite
     * @return width
     */
    public int getWidth(){
        return width;
    }

    /**
     * Returns the height of the sprite
     * @return height
     */
    public int getHeight(){
        return height;
    }

}
