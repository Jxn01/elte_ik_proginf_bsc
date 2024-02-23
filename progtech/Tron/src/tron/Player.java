package tron;

/**
 * Class Player
 *
 * @author jxn
 */
public class Player {

    /**
     * The Player's name
     */
    protected String name;
    /**
     * The Player's score
     */
    protected int score;
    /**
     * The Player's Bike
     */
    protected Bike bike;

    /**
     * Constructs the Player Object, initializes the class variables.
     *
     * @param name The Player's name
     * @param score The Player's score
     * @param bike The Player's Bike
     */
    public Player(String name, int score, Bike bike) {
        this.name = name;
        this.score = score;
        this.bike = bike;
    }
}
