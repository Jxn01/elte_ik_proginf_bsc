package bead1_pst8ra;

/**
 * Class Polygon
 * @author jxn
 */
abstract public class Polygon {

    /**
     * The Coordinate of the Polygon's center.
     */
    protected Coordinate coord;
    
    /**
     * The length of one of the Polygon's sides in centimeters.
     */
    protected int sideLength;
    
    /**
     * The ratio between the area and the perimeter of the Polygon.
     */
    protected double ratio;
  
    /**
     * Constructs a Polygon object from an x and y coordinate and the length of a side.
     * @param x The x coordinate of the center of the polygon.
     * @param y The y coordinate of the center of the polygon.
     * @param sideLength The length of the polygon's side in centimeters.
     * @return A Polygon object.
     */
    public Polygon(int x, int y, int sideLength){
        this.coord = new Coordinate(x, y);
        this.sideLength = sideLength;
    }
    
    /**
     * Sets the center coordinate of the Polygon.
     * @param x The x coord of the center coordinate.
     * @param y The y coord of the center coordinate.
     */
    public void setCoord(int x, int y){
        this.coord = new Coordinate(x, y);
    }

    /**
     * Sets the center coordinate of the Polygon.
     * @param coord The Coordinate of the polygon's center.
     */
    public void setCoord(Coordinate coord){
        this.coord = coord;
    }

    /**
     * @return the center Coordinate of the Polygon.
     */
    public Coordinate getCoord(){
        return coord;
    }

    /**
     * Sets the length of the Polygon's sides.
     * @param sideLength The desired length of a side.
     */
    public void setSideLength(int sideLength){
        this.sideLength = sideLength;
    }

    /**
     * @return The length of one of the Polygon's sides. 
     */
    public int getSideLength(){
        return sideLength;
    }
  
    /**
     * Calculates the Polygon's perimeter.
     * @return The size of the polygon's perimeter in centimeters.
     */
    abstract public double calcPerimeter();
    
    /**
     * Calculates the Polygon's area.
     * @return The size of the polygon's area in square centimeters. 
     */
    abstract public double calcArea();
    
    public double calcRatio(){
        return this.calcPerimeter() / this.calcArea();
    }

    /**
     * Converts the Polygon's data to readable string.
     * @return The polygon's data as a readable string. 
     */
    @Override
    public String toString() {
        return "Polygon: " + coord.toString() + ", oldalhossz: " + sideLength;
    }  
    
}