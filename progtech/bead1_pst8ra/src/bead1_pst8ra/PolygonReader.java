package bead1_pst8ra;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class PolygonReader
 * @author jxn
 */
public class PolygonReader {
    /**
     * An ArrayList storing the Polygon objects.
     */
    private final ArrayList<Polygon> polygons;

    /**
     * Constructs a FileReader object, initializes the ArrayList.
     */
    public PolygonReader() {
        this.polygons = new ArrayList<>();
    }
    
    /**
     * Reads the data from a file and populates the data into a data structure.
     * @param fileName The file's name, from which the data will be read.
     * @throws FileNotFoundException When the specified file can't be found.
     * @throws InvalidInputException When the file reader finds an unknown type of polygon. 
     */
    public void read(String fileName) throws FileNotFoundException, InvalidInputException {
        Scanner sc = new Scanner(new BufferedReader(new java.io.FileReader(fileName)));
        int numPolygons = sc.nextInt();
        while(sc.hasNext()){
            Polygon polygon = switch (sc.next()){
                case "T" -> new Triangle(sc.nextInt(), sc.nextInt(), sc.nextInt());
                case "C" -> new Circle(sc.nextInt(), sc.nextInt(), sc.nextInt());
                case "H" -> new Hexagon(sc.nextInt(), sc.nextInt(), sc.nextInt());
                case "S" -> new Square(sc.nextInt(), sc.nextInt(), sc.nextInt());
                default  -> throw new InvalidInputException("Bad input!");
            };
            if(polygon.sideLength < 1) throw new InvalidInputException("Side length / radius can't be zero or negative!");
            polygons.add(polygon);
        }
        sc.close();
    }
    
    /**
     * Answers the specified problem.
     */
    public Polygon report(){
        Polygon result = polygons.stream().reduce(polygons.get(0), (current, next) -> ((current.calcRatio()) < (next.calcRatio()) ? next : current));
        System.out.println("Az a síkidom, amelynek a területe és a kerülete a legkisebb mértékben tér el egymástól: " + result.toString());
        return result;
    }
    
    /**
     * Clears the Polygon ArrayList.
     */
    public void clear(){
        polygons.clear();
    }
    
}
