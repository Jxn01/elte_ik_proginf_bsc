package bead1_pst8ra;

import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class Main
 * @author jxn
 */
public class Main {
    
    /**
     * @param args The command line arguments.
     */
    public static void main(String[] args) {
        PolygonReader pr = new PolygonReader();
        try {
            pr.read("input.txt");
            pr.report();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidInputException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
