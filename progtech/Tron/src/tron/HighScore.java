package tron;

import java.sql.Timestamp;

/**
 * Class HighScore
 *
 * @author jxn
 */
public class HighScore {

    /**
     * The player's name.
     */
    protected String name;
    /**
     * The player's score.
     */
    protected int score;
    /**
     * The date of the run.
     */
    protected Timestamp ts;

    /**
     * Constructs the HighScore Object, initializes the class variables.
     *
     * @param name The player's name.
     * @param score The player's score.
     * @param ts The date of the run.
     */
    public HighScore(String name, int score, Timestamp ts) {
        this.name = name;
        this.score = score;
        this.ts = ts;
    }
}
