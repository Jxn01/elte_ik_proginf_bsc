/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

/**
 *
 * @author bli
 */
public class PaddlePowerUp extends ActivePowerUp {

    public PaddlePowerUp(Ball ball, Paddle paddle) {
        super(ball, paddle);
    }

    @Override
    public void activate(Ball ball, Paddle paddle) {
        super.activate(ball, paddle);
        paddle.setWidth((int)(paddle.getWidth() * 1.4));
    }
    
    @Override
    public void deactivate(Ball ball, Paddle paddle) {
        super.deactivate(ball, paddle);
        paddle.setWidth((int)(paddle.getWidth() / 1.4));
    }

        

}
