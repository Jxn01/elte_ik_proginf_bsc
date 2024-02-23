package bead1_pst8ra;

/**
 * Class Square
 * @author jxn
 */
public class Square extends Polygon {
  
    /**
     * Constructs a Square object from an x and y coordinate and the length of a side.
     * @param x The x coordinate of the center of the square.
     * @param y The y coordinate of the center of the square.
     * @param sideLength The length of the square's side in centimeters.
     * @return A Square object.
     */
    public Square(int x, int y, int sideLength){
        super(x, y, sideLength);
    }

    /**
     * Calculates the Square's perimeter.
     * @return The size of the square's perimeter in centimeters.
     */
    @Override
    public double calcPerimeter(){
      return 4 * sideLength;
    }

    /**
     * Calculates the Square's area.
     * @return The size of the square's area in square centimeters. 
     */
    @Override
    public double calcArea(){
        return Math.pow(sideLength, 2);
    }
    
    /**
     * Converts the Square's data to readable string.
     * @return The square's data as a readable string. 
     */
    @Override
    public String toString() {
        return "Négyzet: " + coord.toString() + ", oldalhossz: " + sideLength;
    }  
    
}
