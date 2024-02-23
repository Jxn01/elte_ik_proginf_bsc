package bead1_pst8ra;

/**
 * Class Coordinate
 * @author jxn
 */
public class Coordinate{
    
    /**
     * The x coordinate.
     */
    private int x;
    
    /**
     * The y coordinate.
     */
    private int y;
  
    /**
     * Constructs a Coordinate object from an x and y coordinate.
     * @param x The x coordinate.
     * @param y The y coordinate.
     */
    public Coordinate(int x, int y){
        this.x = x;
        this.y = y;
    }
    
    /**
     * Sets the x coordinate.
     * @param x The desired x coordinate.
     */
    public void setX(int x){
        this.x = x;
    }

    /**
     * @return The x coordinate.
     */
    public int getX(){
        return x;
    }

    /**
     * Sets the y coordinate.
     * @param y The desired y coordinate.
     */
    public void setY(int y){
        this.y = y;
    }

    /**
     * @return The y coordinate. 
     */
    public int getY(){
        return y;
    }

    /**
     * Converts the Coordinate's data to a readable string.
     * @return The coordinate in (x, y) form.
     */
    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }

}