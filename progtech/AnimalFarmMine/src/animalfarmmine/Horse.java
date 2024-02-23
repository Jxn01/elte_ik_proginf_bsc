package animalfarmmine;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public class Horse extends Animal{

    public Horse(String name, int weight, ArrayList<Integer> meals) {
        super(name, weight, meals);
        setVERYSLIM(60);
    }

    public Horse() {
        setVERYSLIM(60);
    }
    
    
    
}
