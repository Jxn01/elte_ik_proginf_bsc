package tron;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Class Sprite
 *
 * @author jxn
 */
public class Sprite {

    /**
     * The Sprite's x coordinate.
     */
    protected int x;
    /**
     * The Sprite's y coordinate.
     */
    protected int y;
    /**
     * The Sprite's width.
     */
    protected int width;
    /**
     * The Sprite's height.
     */
    protected int height;
    /**
     * The Sprite's Color.
     */
    protected Color color;

    /**
     * Constructs the Sprite Object, initializes the class variables.
     *
     * @param x The Sprite's x coordinate.
     * @param y The Sprite's y coordinate.
     * @param width The Sprite's width.
     * @param height The Sprite's height.
     * @param color The Sprite's Color.
     */
    public Sprite(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Draws the Sprite.
     *
     * @param g Graphics component to draw on.
     * @param bike True if the sprite is a Bike.
     */
    public void draw(Graphics g, boolean bike) {
        if (bike) {
            g.setColor(Color.BLACK);
            g.fillOval(x, y, width, height);
        } else {
            g.setColor(this.color);
            g.fillOval(x, y, width, height);
        }
    }

    /**
     * Checks if the Sprite has collided with another Sprite.
     *
     * @param other The other Sprite
     * @return True if they collided.
     */
    public boolean collides(Sprite other) {
        Rectangle rect = new Rectangle(x, y, width, height);
        Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
        return rect.intersects(otherRect);
    }
}
