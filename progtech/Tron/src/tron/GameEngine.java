package tron;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

/**
 *
 * @author jxn
 */
public class GameEngine extends JPanel {

    /**
     * The Game which the GameEngine works on.
     */
    private Game game;
    /**
     * The fps of the game.
     */
    private final int FPS = 60;
    /**
     * The Timer which will update the game every "tick".
     */
    private Timer gameUpdateTimer;
    /**
     * The Bikes' speed.
     */
    private final int vel = 3;

    /**
     * Constructs the GameEngine, initializes the class variables. Sets the
     * Keyboard events for WASD and the arrows. Starts the game.
     *
     * @param player1 Player 1's name
     * @param player2 Player 2's name
     * @param color1 Player 1's color
     * @param color2 Player 2's color
     */
    public GameEngine(String player1, String player2, Color color1, Color color2) {
        super();
        Bike bike1 = new Bike(200, 600, 28, 28, vel, Direction.UP, color1);
        Bike bike2 = new Bike(600, 600, 28, 28, vel, Direction.UP, color2);
        Player p1 = new Player(player1, 1, bike1);
        Player p2 = new Player(player2, 1, bike2);
        this.game = new Game(p1, p2);
        this.setBackground(Color.LIGHT_GRAY);
        this.setPreferredSize(new Dimension(800, 600));

        this.getInputMap(2).put(KeyStroke.getKeyStroke('a'), "pressed left (player 1)");
        this.getActionMap().put("pressed left (player 1)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player1.bike.changeDirection(Direction.LEFT);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke('w'), "pressed up (player 1)");
        this.getActionMap().put("pressed up (player 1)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player1.bike.changeDirection(Direction.UP);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke('s'), "pressed down (player 1)");
        this.getActionMap().put("pressed down (player 1)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player1.bike.changeDirection(Direction.DOWN);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke('d'), "pressed right (player 1)");
        this.getActionMap().put("pressed right (player 1)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player1.bike.changeDirection(Direction.RIGHT);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke("LEFT"), "pressed left (player 2)");
        this.getActionMap().put("pressed left (player 2)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player2.bike.changeDirection(Direction.LEFT);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke("UP"), "pressed up (player 2)");
        this.getActionMap().put("pressed up (player 2)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player2.bike.changeDirection(Direction.UP);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke("DOWN"), "pressed down (player 2)");
        this.getActionMap().put("pressed down (player 2)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player2.bike.changeDirection(Direction.DOWN);
            }
        });

        this.getInputMap(2).put(KeyStroke.getKeyStroke("RIGHT"), "pressed right (player 2)");
        this.getActionMap().put("pressed right (player 2)", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                game.player2.bike.changeDirection(Direction.RIGHT);
            }
        });
        start();
    }

    /**
     *
     * @return True if the game has ended.
     */
    public boolean over() {
        return game.isGameOver();
    }

    /**
     * Paints the JPanel, the Bikes, and their trails.
     *
     * @param grphcs A Graphics Object
     */
    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        game.player1.bike.draw(grphcs, true);
        game.player2.bike.draw(grphcs, true);
        for (Trail t : game.player1.bike.trail) {
            t.draw(grphcs, false);
        }

        for (Trail t : game.player2.bike.trail) {
            t.draw(grphcs, false);
        }
    }

    /**
     * Starts the game.
     */
    private void start() {
        gameUpdateTimer = new Timer(1000 / FPS, new Update());
        gameUpdateTimer.start();
    }

    /**
     * Class Update It updates the game and calls repaint() every tick.
     */
    private class Update implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            game.update();
            if (game.isGameOver()) {
                gameUpdateTimer.stop();
            }
            repaint();
        }
    }

    /**
     *
     * @return FPS
     */
    public int getFPS() {
        return FPS;
    }

    /**
     *
     * @return Game Object
     */
    public Game getGame() {
        return game;
    }

}
