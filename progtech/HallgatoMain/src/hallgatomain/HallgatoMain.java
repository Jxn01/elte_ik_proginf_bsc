package hallgatomain;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public class HallgatoMain {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Hallgato h1 = new Hallgato("Béla", "Magyar", 2.2);
        Hallgato h2 = new Hallgato("Árpád", "Magyar", 2.5);
        Hallgato h3 = new Hallgato("Tamás", "Magyar", 4.2);
        Hallgato h4 = new Hallgato("Péter", "Magyar", 3.6);
        
        Hallgatok h = new Hallgatok();
        
        h.hallgatok.add(h1);
        h.hallgatok.add(h2);
        h.hallgatok.add(h3);
        h.hallgatok.add(h4);
        
        Hallgato best = h.getBestAvg();
        Hallgato worst = h.getWorstAvg();
        
        System.out.println("Legjobb: "+best.getName()+", "+best.getAvg());
        System.out.println("Legrosszabb: "+worst.getName()+", "+worst.getAvg());
        
        System.out.println("\nÖsztöndíjas hallgatók:");
        
        ArrayList<Hallgato> osztondijasok = h.getOsztondijasok();
        
        for(Hallgato elem : osztondijasok){
            System.out.println(elem.getName());
        }
    }
    
}
