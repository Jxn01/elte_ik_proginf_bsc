package tron;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.Timer;

/**
 * Class TronGUI
 *
 * @author jxn
 */
public class TronGUI {

    /**
     * The JFrame of the GUI application
     */
    private JFrame frame;
    /**
     * The JPanel where the game will be played
     */
    private GameEngine gameArea;
    /**
     * The JPanel where the highscores will be shown
     */
    private HighScorePanel hsPanel;
    /**
     * The Timer which checks if the game is over yet.
     */
    private Timer gameOverTimer = new Timer(1000 / 60, new Update());
    /**
     * The Timer which will act as a "timer" in game.
     */
    private Timer timer;

    /**
     * Constructs the TronGUI, initializes the class variables.
     */
    public TronGUI() {
        frame = new JFrame("Tron");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Játék");
        JMenuItem newGameButton = new JMenuItem("Új játék kezdése");
        JMenuItem highscoresButton = new JMenuItem("Pontszámok");
        JMenuItem exitButton = new JMenuItem("Kilépés");
        frame.setJMenuBar(menuBar);
        menuBar.add(gameMenu);
        gameMenu.add(newGameButton);
        gameMenu.add(highscoresButton);
        gameMenu.add(exitButton);
        JLabel timeLabel = new JLabel(" ");
        timeLabel.setHorizontalAlignment(JLabel.RIGHT);

        newGameButton.addActionListener((ActionEvent e) -> {
            startGameEngine();
            startTimer(timeLabel);
            frame.pack();
        });

        highscoresButton.addActionListener((ActionEvent e) -> {
            gameOverTimer.stop();
            frame.getContentPane().removeAll();
            hsPanel = new HighScorePanel(null);
            frame.getContentPane().add(hsPanel);
            frame.pack();
        });

        exitButton.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });

        frame.setPreferredSize(new Dimension(800, 600));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     *
     * @param time Time in ms.
     * @return Time in mm:ss format.
     */
    private String formatTime(long time) {
        long minutes = time / 60000;
        long seconds = (time % 60000) / 1000;
        return String.format("%02d:%02d", minutes, seconds);
    }

    /**
     * Starts the GameEngine, asks for players' name, and colour.
     */
    private void startGameEngine() {
        gameOverTimer.stop();

        String player1 = null;
        while (player1 == null || player1.equals("")) {
            player1 = (String) JOptionPane.showInputDialog(frame, "Add meg az 1. játékos nevét!", "Név", JOptionPane.QUESTION_MESSAGE, null, null, null);
        }

        String player2 = null;
        while (player2 == null || player2.equals("") || player1.equals(player2)) {
            player2 = (String) JOptionPane.showInputDialog(frame, "Add meg az 2. játékos nevét!", "Név", JOptionPane.QUESTION_MESSAGE, null, null, null);
        }

        Color color1 = null;
        while (color1 == null) {
            color1 = JColorChooser.showDialog(frame, "1. játékos színe", Color.red);
        }

        Color color2 = null;
        while (color2 == null || color1 == color2) {
            color2 = JColorChooser.showDialog(frame, "2. játékos színe", Color.blue);
        }

        frame.getContentPane().removeAll();
        gameArea = new GameEngine(player1, player2, color1, color2);
        frame.getContentPane().add(gameArea);
        gameOverTimer.start();
    }

    /**
     * Starts the timer.
     *
     * @param timeLabel The label where the timer will be shown.
     */
    private void startTimer(JLabel timeLabel) {
        long startTime = System.currentTimeMillis();
        timer = new Timer(10, (ActionEvent ee) -> {
            timeLabel.setText("Eltelt idő: " + formatTime(System.currentTimeMillis() - startTime));
        });
        frame.getContentPane().add(timeLabel, BorderLayout.SOUTH);
        timer.start();
    }

    /**
     * Class Update Every tick, it checks if the game has ended yet. If the game
     * has ended, shows the highscore screen and sends the winner to the
     * database.
     */
    private class Update implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            if (gameArea.over()) {
                gameOverTimer.stop();
                timer.stop();
                Player winner = gameArea.getGame().winner;
                frame.getContentPane().removeAll();
                hsPanel = new HighScorePanel(winner);
                frame.getContentPane().add(hsPanel);
                frame.pack();
            }
        }
    }
}
