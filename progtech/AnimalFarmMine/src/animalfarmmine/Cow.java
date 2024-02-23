package animalfarmmine;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public class Cow extends Animal{

    public Cow(String name, int weight, ArrayList<Integer> meals) {
        super(name, weight, meals);
        setVERYSLIM(100);
    }

    public Cow() {
        setVERYSLIM(100);
    }
    
    
    
}
