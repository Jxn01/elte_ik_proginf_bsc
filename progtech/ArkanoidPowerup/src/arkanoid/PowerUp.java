/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arkanoid;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author bli
 */
public class PowerUp extends Sprite {

    protected double vely;
    protected PowerUpEnum type;

    public PowerUp(int x, int y, int width, int height, PowerUpEnum type) {
        super(x, y, width, height, null);
        this.vely = 1;
        this.type = type;
        switch (type) {
            case PADDLE:
                image = new ImageIcon("data/images/paddle_powerup.png").getImage();
                break;
            case WALL:
                image = new ImageIcon("data/images/wall_powerup.png").getImage();
                break;
        }
    }

    public void move() {
        y += vely;
    }

    public PowerUpEnum getType() {
        return type;
    }
}
