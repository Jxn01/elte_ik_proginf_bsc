/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arkanoid;

/**
 *
 * @author bli
 */
public class ActivePowerUp {

    protected long duration = 10000;
    private long start;

    public ActivePowerUp(Ball ball, Paddle paddle) {
        activate(ball, paddle);
    }
    
    public ActivePowerUp(Ball ball, Paddle paddle, long duration) {
        this(ball, paddle);
        this.duration = duration;
    }

    public void activate(Ball ball, Paddle paddle) {
        start = System.currentTimeMillis();
    }

    public void deactivate(Ball ball, Paddle paddle) {
    }

    public boolean check(Ball ball, Paddle paddle) {
        if (System.currentTimeMillis() - start > duration) {
            deactivate(ball, paddle);
            return false;
        }
        return true;
    }
    
    public long getDuration() {
        return duration;
    }
}
