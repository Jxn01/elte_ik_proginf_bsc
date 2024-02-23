/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package arkanoid;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author bli
 */
public class GameEngine extends JPanel {
    
    private final int FPS = 240;
    private final int PADDLE_Y = 550;
    private final int PADDLE_WIDTH = 100;
    private final int PADDLE_HEIGHT = 20;
    private final int PADDLE_MOVEMENT = 2;
    private final int BALL_RADIUS = 20;
    private final double POWERUP_RATE = 0.4;
    
    private boolean paused = false;
    private Image background;
    private int levelNum = 0;
    private Level level;
    private Ball ball;
    private Paddle paddle;
    private Timer newFrameTimer;
    
    private ArrayList<PowerUp> powerUps;
    private ArrayList<ActivePowerUp> activePowerUps;
    
    public GameEngine() {
        super();
        background = new ImageIcon("data/images/background.jpg").getImage();
        this.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "pressed left");
        this.getActionMap().put("pressed left", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("xd");
                paddle.setVelx(-PADDLE_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "pressed right");
        this.getActionMap().put("pressed right", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paddle.setVelx(PADDLE_MOVEMENT);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "pressed down");
        this.getActionMap().put("pressed down", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paddle.setVelx(0);
            }
        });
        this.getInputMap().put(KeyStroke.getKeyStroke("ESCAPE"), "escape");
        this.getActionMap().put("escape", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                paused = !paused;
            }
        });
        restart();
        newFrameTimer = new Timer(1000 / FPS, new NewFrameListener());
        newFrameTimer.start();
        
        powerUps = new ArrayList();
        activePowerUps = new ArrayList();
    }
    
    public void restart() {
        try {
            level = new Level("data/levels/level0" + levelNum + ".txt");
        } catch (IOException ex) {
            Logger.getLogger(GameEngine.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        Image paddleImage = new ImageIcon("data/images/paddle.png").getImage();
        paddle = new Paddle(350, PADDLE_Y, PADDLE_WIDTH, PADDLE_HEIGHT, paddleImage);
        Image ballImage = new ImageIcon("data/images/ball.png").getImage();
        ball = new Ball(400, 300, BALL_RADIUS, BALL_RADIUS, ballImage);
    }
    
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.drawImage(background, 0, 0, 800, 600, null);
        level.draw(grphcs);
        paddle.draw(grphcs);
        ball.draw(grphcs);
        for (PowerUp powerUp : powerUps) {
            powerUp.draw(grphcs);
        }
        if (ball.isWalled()) {
            grphcs.fillRect(0, 550, 800, 5);
        }
    }
    
    public void maybeCreatePowerUp(Brick fromBrick) {
        if (Math.random() < POWERUP_RATE) {
            if (Math.random() < 0.5) {
                powerUps.add(new PowerUp(fromBrick.getX(), fromBrick.getY(),
                        fromBrick.getWidth(), fromBrick.getHeight(), PowerUpEnum.PADDLE));
            } else {
                powerUps.add(new PowerUp(fromBrick.getX(), fromBrick.getY(),
                        fromBrick.getWidth(), fromBrick.getHeight(), PowerUpEnum.WALL));
            }
        }
    }
    
    class NewFrameListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent ae) {
            if (!paused) {
                ArrayList<PowerUp> toRemove = new ArrayList<>();
                for (PowerUp powerUp : powerUps) {
                    powerUp.move();
                    if (powerUp.collides(paddle)) {
                        switch (powerUp.getType()) {
                            case PADDLE:
                                activePowerUps.add(new PaddlePowerUp(ball, paddle));
                                break;
                            case WALL:
                                activePowerUps.add(new WallPowerUp(ball, paddle));
                                break;
                        }
                        toRemove.add(powerUp);
                    }
                    if (powerUp.getY() > 600) {
                        toRemove.add(powerUp);
                    }
                }
                powerUps.removeAll(toRemove);
                ArrayList<ActivePowerUp> toRemoveActive = new ArrayList<>();
                for (ActivePowerUp powerUp : activePowerUps) {
                    if (!powerUp.check(ball, paddle)) {
                        toRemoveActive.add(powerUp);
                    }
                }
                if (toRemoveActive.stream().anyMatch(ap -> ap.getDuration() != 1000)) {
                    activePowerUps.add(new WallPowerUp(ball, paddle, 1000));
                }
                activePowerUps.removeAll(toRemoveActive);
                
                ball.moveX();
                Brick collidedBrick = level.collides(ball);
                if (collidedBrick != null) {
                    ball.invertVelX();
                    maybeCreatePowerUp(collidedBrick);
                }
                if (!ball.moveY()) {
                    levelNum = 0;
                    restart();
                    return;
                }
                collidedBrick = level.collides(ball);
                if (collidedBrick != null) {
                    ball.invertVelY();
                    maybeCreatePowerUp(collidedBrick);
                }
                if (paddle.collides(ball)) {
                    ball.invertVelY();
                }
                paddle.move();
            }
            if (level.isOver()) {
                levelNum = (levelNum + 1) % 2;
                restart();
            }
            repaint();
        }
        
    }
}
