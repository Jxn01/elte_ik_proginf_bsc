package tron;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;

/**
 * Class HighScoreTableModel
 *
 * @author jxn
 */
public class HighScoreTableModel extends AbstractTableModel {

    /**
     * A List containing the 10 HighScores
     */
    private final List<HighScore> highScores;
    /**
     * A String array containing the columns' names.
     */
    private final String[] colName = new String[]{"Név", "Eddigi pontszám", "Legutóbbi dátum"};

    /**
     * Constructs the HighScoreTableModel Object, initializes the class
     * variables.
     *
     * @param highScores All the HighScores
     */
    public HighScoreTableModel(ArrayList<HighScore> highScores) {
        int end = highScores.size() >= 10 ? 10 : highScores.size();
        this.highScores = highScores.subList(0, end);
    }

    /**
     *
     * @return rowcount
     */
    @Override
    public int getRowCount() {
        return highScores.size();
    }

    /**
     *
     * @return columncount
     */
    @Override
    public int getColumnCount() {
        return 3;
    }

    /**
     *
     * @param row Row index
     * @param col Col index
     * @return The element at row x, col y
     */
    @Override
    public Object getValueAt(int row, int col) {
        HighScore sc = highScores.get(row);
        switch (col) {
            case 0 -> {
                return sc.name;
            }
            case 1 -> {
                return sc.score;
            }
            case 2 -> {
                return sc.ts.toString().substring(0, 19);
            }
        }
        return null;
    }

    /**
     *
     * @param i
     * @return The ith columns name.
     */
    @Override
    public String getColumnName(int i) {
        return colName[i];
    }
}
