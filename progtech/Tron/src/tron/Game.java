package tron;

/**
 * Class Game
 *
 * @author jxn
 */
public class Game {

    /**
     * Player 1's Player Object
     */
    protected Player player1;
    /**
     * Player 2's Player Object
     */
    protected Player player2;
    /**
     * The winner Player
     */
    protected Player winner;

    /**
     * Constructs the Game Object.
     *
     * @param player1 The first Player
     * @param player2 The second Player
     */
    public Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Moves the bikes, and then checks if they crashed or not.
     */
    public void update() {
        player1.bike.move();
        player2.bike.move();
        crashUpdate();
    }

    /**
     * Checks if the bikes crashed or not.
     */
    private void crashUpdate() {
        player1.bike.didCrash(player2.bike);
        player2.bike.didCrash(player1.bike);
    }

    /**
     * Checks if the game is over yet.
     *
     * @return True if the game has ended.
     */
    public boolean isGameOver() {
        if (player1.bike.crashed) {
            winner = player2;
            return true;
        } else if (player2.bike.crashed) {
            winner = player1;
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @return player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     *
     * @return player2
     */
    public Player getPlayer2() {
        return player2;
    }

}
