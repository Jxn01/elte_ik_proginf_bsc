package rubikTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * Class RubikTableGUI
 *
 * @author jxn
 */
public class RubikTableGUI {

    /**
     * A JFrame as the GUI of the game.
     */
    private JFrame frame;

    /**
     * An int, it stores how many times the table has been shifted.
     */
    private int buttonsPressed = 0;
    /**
     * A ColorMatrix, representing the RubikTable, in-code.
     */
    private ColorMatrix colors;

    /**
     * Constructs a RubikTableGUI Object, initializes the class variables, adds
     * a menubar to the frame. To the menubar, it adds an exit button, and a
     * drop down list, containing 3 buttons for the 3 possible tables (2x2, 4x4,
     * 6x6). It attaches an ActionListener to the buttons, which will call the
     * drawTable function when the buttons are clicked.
     */
    public RubikTableGUI() {
        frame = new JFrame("Rubik tábla");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);
        JMenu gameMenu = new JMenu("Játék");
        menuBar.add(gameMenu);
        JMenu newMenu = new JMenu("Új játék");
        gameMenu.add(newMenu);
        frame.setVisible(true);
        int[] sizes = new int[]{2, 4, 6};
        for (int n : sizes) {
            JMenuItem sizeMenuItem = new JMenuItem(n + "x" + n);
            newMenu.add(sizeMenuItem);
            sizeMenuItem.addActionListener((ActionEvent e) -> {
                drawTable(n, true);
            });
        }

        JMenuItem exitMenuItem = new JMenuItem("Kilépés");
        gameMenu.add(exitMenuItem);
        exitMenuItem.addActionListener((ActionEvent e) -> {
            System.exit(0);
        });
    }

    /**
     * Draws the table and generates buttons around the table to shift the rows
     * and columns. If the newGame parameter is true, then resets the
     * buttonsPressed, frame and colors variables. If newGame is false, then
     * increments buttonsPressed by one.
     *
     * @param n The table's size.
     * @param newGame True if the table is part of a new game, if it is part of
     * an existing game, it is false;
     */
    public void drawTable(int n, boolean newGame) {
        if (newGame) {
            buttonsPressed = 0;
            frame.getContentPane().removeAll();
            colors = new ColorMatrix(n);
        } else {
            buttonsPressed++;
        }

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(n + 2, n + 2));
        for (int i = 0; i < n + 2; i++) {
            for (int j = 0; j < n + 2; j++) {
                final int i2 = i;
                final int j2 = j;
                if ((i == 0 && j == 0)
                        || (i == 0 && j == n + 1)
                        || (i == n + 1 && j == 0)
                        || (i == n + 1 && j == n + 1)) {
                    JPanel dummy = new JPanel();
                    dummy.setPreferredSize(new Dimension(80, 80));
                    panel.add(dummy);

                } else if ((i == 0 && (j != 0 && j != n + 1))) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(80, 80));
                    button.setText("^");
                    button.addActionListener((ActionEvent e) -> {
                        colors.shiftUp(j2 - 1);
                        drawTable(colors.getN(), false);
                        if (colors.isComplete()) {
                            gameCompleted();
                        }
                    });
                    panel.add(button);

                } else if (i == n + 1 && (j != 0 && j != n + 1)) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(80, 80));
                    button.setText("v");
                    button.addActionListener((ActionEvent e) -> {
                        colors.shiftDown(j2 - 1);
                        drawTable(colors.getN(), false);
                        if (colors.isComplete()) {
                            gameCompleted();
                        }
                    });
                    panel.add(button);

                } else if (j == 0 && (i != 0 && i != n + 1)) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(80, 80));
                    button.setText("<");
                    button.addActionListener((ActionEvent e) -> {
                        colors.shiftLeft(i2 - 1);
                        drawTable(colors.getN(), false);
                        if (colors.isComplete()) {
                            gameCompleted();
                        }
                    });
                    panel.add(button);

                } else if (j == n + 1 && (i != 0 && i != n + 1)) {
                    JButton button = new JButton();
                    button.setPreferredSize(new Dimension(80, 80));
                    button.setText(">");
                    button.addActionListener((ActionEvent e) -> {
                        colors.shiftRight(i2 - 1);
                        drawTable(colors.getN(), false);
                        if (colors.isComplete()) {
                            gameCompleted();
                        }
                    });
                    panel.add(button);

                } else {
                    JPanel square = new JPanel();
                    square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                    square.setPreferredSize(new Dimension(80, 80));
                    square.setBackground(colors.getMatrix()[i - 1][j - 1]);
                    panel.add(square);
                }
            }

        }
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        frame.pack();
    }

    /**
     * Shows a message dialog to the player, calls Thread.sleep() for 4 seconds,
     * and begins a new game.
     */
    public void gameCompleted() {
        JOptionPane.showMessageDialog(frame, "Gratulálok, nyertél! (Lépések száma: " + buttonsPressed + ") \nEgy új játék indul egy pár másodperc múlva...");
        try {
            Thread.sleep(4000);
        } catch (InterruptedException ex) {
            Logger.getLogger(RubikTableGUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        drawTable(colors.getN(), true);
    }
}
