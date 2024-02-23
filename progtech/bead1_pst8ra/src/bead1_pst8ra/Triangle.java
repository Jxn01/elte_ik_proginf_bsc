package bead1_pst8ra;

/**
 * Class Triangle
 * @author jxn
 */
public class Triangle extends Polygon {

    /**
     * Constructs a Triangle object from an x and y coordinate and the length of a side.
     * @param x The x coordinate of the center of the triangle.
     * @param y The y coordinate of the center of the triangle.
     * @param sideLength The length of the triangle's side in centimeters.
     * @return A Triangle object.
     */
    public Triangle(int x, int y, int sideLength){
        super(x, y, sideLength);
    }
    
    /**
     * Calculates the Triangle's perimeter.
     * @return The size of the triangle's perimeter in centimeters.
     */
    @Override
    public double calcPerimeter(){
        return 3 * sideLength;
    }

    /**
     * Calculates the Triangle's area.
     * @return The size of the triangle's area in square centimeters. 
     */
    @Override
    public double calcArea(){
        return (sideLength * ((Math.sqrt(3.0)/2) * sideLength)) / 2;
    }
    
    /**
     * Converts the Triangle's data to readable string.
     * @return The triangle's data as a readable string. 
     */
    @Override
    public String toString() {
        return "Háromszög: " + coord.toString() + ", oldalhossz: " + sideLength;
    }  

}
