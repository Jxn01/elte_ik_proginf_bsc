/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arkanoid;

/**
 *
 * @author bli
 */
public class WallPowerUp extends ActivePowerUp {

    public WallPowerUp(Ball ball, Paddle paddle) {
        super(ball, paddle);
    }

    public WallPowerUp(Ball ball, Paddle paddle, long duration) {
        super(ball, paddle, duration);
    }
    
    @Override
    public void activate(Ball ball, Paddle paddle) {
        super.activate(ball, paddle);
        ball.setWalled(true);
    }

    @Override
    public void deactivate(Ball ball, Paddle paddle) {
        super.deactivate(ball, paddle);
        ball.setWalled(false);
    }

}
