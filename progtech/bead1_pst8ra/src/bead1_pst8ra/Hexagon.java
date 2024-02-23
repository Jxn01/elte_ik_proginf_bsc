package bead1_pst8ra;

/**
 * Class Hexagon
 * @author jxn
 */
public class Hexagon extends Polygon {
  
    /**
     * Constructs a Hexagon object from an x and y coordinate and the length of a side.
     * @param x The x coordinate of the center of the hexagon.
     * @param y The y coordinate of the center of the hexagon.
     * @param sideLength The length of the hexagon's side in centimeters.
     * @return A Hexagon object.
     */
    public Hexagon(int x, int y, int sideLength){
        super(x, y, sideLength);
    }
    
    /**
     * Calculates the Hexagon's perimeter.
     * @return The size of the hexagon's perimeter in centimeters.
     */
    @Override
    public double calcPerimeter(){
        return 6 * sideLength;
    }
    
    /**
     * Calculates the Hexagon's area.
     * @return The size of the hexagon's area in square centimeters. 
     */
    @Override
    public double calcArea(){
        return ((3 * Math.sqrt(3.0)) / 2) * Math.pow(sideLength, 2);
    }
    
    /**
     * Converts the Hexagon's data to readable string.
     * @return The hexagon's data as a readable string. 
     */
    @Override
    public String toString() {
        return "Hatszög: " + coord.toString() + ", oldalhossz: " + sideLength;
    }  

}
