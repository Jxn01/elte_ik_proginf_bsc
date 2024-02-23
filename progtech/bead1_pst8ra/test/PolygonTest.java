
import bead1_pst8ra.*;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author jxn
 */
public class PolygonTest {

    @Test
    public void test1() {
        Polygon p = null;
        Polygon expected = new Circle(0, 0, 1);
        PolygonReader pr = new PolygonReader();
        try {
            pr.read("wbinput1.txt");
            p = pr.report();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidInputException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(p.calcRatio(), expected.calcRatio(), 0.001);
    }

    @Test
    public void test2() {
        Polygon p = null;
        Polygon expected = new Square(0, 0, 1);
        PolygonReader pr = new PolygonReader();
        try {
            pr.read("wbinput2.txt");
            p = pr.report();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidInputException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(p.calcRatio(), expected.calcRatio(), 0.001);
    }

    @Test
    public void test3() {
        Polygon p = null;
        Polygon expected = new Triangle(0, 0, 10);
        PolygonReader pr = new PolygonReader();
        try {
            pr.read("wbinput3.txt");
            p = pr.report();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidInputException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        assertEquals(p.calcRatio(), expected.calcRatio(), 0.001);
    }

    @Test(expected = InvalidInputException.class)
    public void test4() throws InvalidInputException, FileNotFoundException {
        Polygon p = null;
        Polygon expected;
        PolygonReader pr = new PolygonReader();
        pr.read("wbinput4.txt");
        p = pr.report();

    }

    @Test(expected = InvalidInputException.class)
    public void test5() throws FileNotFoundException, InvalidInputException {
        Polygon p = null;
        Polygon expected;
        PolygonReader pr = new PolygonReader();
        pr.read("wbinput5.txt");
        p = pr.report();

    }

    @Test(expected = FileNotFoundException.class)
    public void test6() throws FileNotFoundException, InvalidInputException {
        Polygon p = null;
        Polygon expected;
        PolygonReader pr = new PolygonReader();
        pr.read("wbinput6.txt");
        p = pr.report();

    }

}
