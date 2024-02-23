package bead1_pst8ra;

/**
 * Class Circle
 * @author jxn
 */
public class Circle extends Polygon {
  
    /**
     * Constructs a Circle object from an x and y coordinate and the radius.
     * @param x The x coordinate of the center of the circle.
     * @param y The y coordinate of the center of the circle.
     * @param radius The length of the radius in centimeters.
     * @return A Circle object.
     */
    public Circle(int x, int y, int radius){
        super(x, y, radius);
    }

    /**
     * Calculates the Circle's perimeter.
     * @return The size of the circle's perimeter in centimeters.
     */
    @Override
    public double calcPerimeter(){
        return 2 * sideLength * Math.PI;
    }

    /**
     * Calculates the Circle's area.
     * @return The size of the circle's area in square centimeters. 
     */
    @Override
    public double calcArea(){
        return Math.pow(sideLength, 2) * Math.PI;
    }
    
    /**
     * Converts the Circle's data to readable string.
     * @return The circle's data as a readable string. 
     */
    @Override
    public String toString() {
        return "Kör: " + coord.toString() + ", oldalhossz: " + sideLength;
    }  

}
