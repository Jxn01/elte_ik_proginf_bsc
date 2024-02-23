package tron;

import java.awt.Color;
import java.util.ArrayList;

/**
 * Class Bike
 *
 * @author jxn
 */
public class Bike extends Sprite {

    /**
     * The velocity of the bike.
     */
    protected int vel;
    /**
     * The Direction of the bike.
     */
    protected Direction dir;
    /**
     * The trail of the bike, which consists of Trail objects.
     */
    protected ArrayList<Trail> trail;
    /**
     * True if the bike has crashed.
     */
    protected boolean crashed;

    /**
     * Constructs the Bike object, initializes the class variables.
     *
     * @param x the x position of the bike.
     * @param y the y position of the bike.
     * @param width the width of the bike.
     * @param height the height of the bike.
     * @param vel the velocity of the bike.
     * @param dir the Direction of the bike.
     * @param color the color of the bike's trail.
     */
    public Bike(int x, int y, int width, int height, int vel, Direction dir, Color color) {
        super(x, y, width, height, color);
        this.vel = vel;
        this.dir = dir;
        this.trail = new ArrayList<>();
        this.crashed = false;
    }

    /**
     * It changes the bikes direction.
     *
     * @param newdir The new Direction.
     */
    public void changeDirection(Direction newdir) {
        switch (newdir) {
            case LEFT -> {
                dir = dir != Direction.RIGHT ? newdir : dir;
            }
            case RIGHT -> {
                dir = dir != Direction.LEFT ? newdir : dir;
            }
            case UP -> {
                dir = dir != Direction.DOWN ? newdir : dir;
            }
            case DOWN -> {
                dir = dir != Direction.UP ? newdir : dir;
            }
        }
    }

    /**
     * It moves the bike.
     */
    public void move() {
        trail.add(new Trail(x + 10, y + 10, 8, 8, color));
        switch (dir) {
            case LEFT -> {
                x -= vel;
            }
            case RIGHT -> {
                x += vel;
            }
            case UP -> {
                y -= vel;
            }
            case DOWN -> {
                y += vel;
            }
        }
    }

    /**
     * It checks whether the bike has crashed or not.
     *
     * @param otherBike The other player's bike.
     * @return True if the bike has crashed.
     */
    public boolean didCrash(Bike otherBike) {
        if (x < 0 || y < 0 || x > 800 || y > 600) {
            crashed = true;
        }

        for (Trail t : otherBike.trail) {
            if (this.collides(t)) {
                crashed = true;
            }
        }
        return crashed;
    }
}
