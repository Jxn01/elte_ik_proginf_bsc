package tron;

import java.awt.Dimension;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 * Class HighScorePanel
 *
 * @author jxn
 */
public class HighScorePanel extends JPanel {

    /**
     * The Database from which the panel gets the information.
     */
    private Database db;
    /**
     * The JTable where the data will be displayed.
     */
    private JTable table;
    /**
     * The model for the table.
     */
    private HighScoreTableModel model;

    /**
     * Constructs the HighScorePanel object, initializes the class variables.
     * Fills the table with the data from the database.
     *
     * @param winner The winner Player. Null if the Panel is accessed from the
     * menubar.
     */
    public HighScorePanel(Player winner) {
        super();
        try {
            if (winner != null) {
                HighScore newScore = new HighScore(winner.name, winner.score, new Timestamp(System.currentTimeMillis()));
                this.db = new Database(newScore);
            } else {
                this.db = new Database(null);
            }
            model = new HighScoreTableModel(db.getHighScores());
            table = new JTable(model);
            table.setFillsViewportHeight(true);
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(600, 400));
            add(scrollPane);
        } catch (SQLException ex) {
            Logger.getLogger(HighScorePanel.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
