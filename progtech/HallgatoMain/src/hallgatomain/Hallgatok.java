package hallgatomain;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public class Hallgatok {
    public ArrayList<Hallgato> hallgatok;

    public Hallgatok(ArrayList<Hallgato> hallgatok) {
        this.hallgatok = hallgatok;
    }
    
    public Hallgatok(){
        this.hallgatok = new ArrayList<Hallgato>();
    }
    
    public Hallgato getBestAvg(){
       if(!hallgatok.isEmpty()){
            Hallgato hallgato = hallgatok.get(0);
            double max = hallgatok.get(0).getAvg();
            for(Hallgato elem : hallgatok){
                if(elem.getAvg() > max){
                    max = elem.getAvg();
                    hallgato = elem;
                }
            }
            return hallgato;
       }else{
            return null;
       }
    }
    
    public Hallgato getWorstAvg(){
        if(!hallgatok.isEmpty()){
            Hallgato hallgato = hallgatok.get(0);
            double min = hallgatok.get(0).getAvg();
            for(Hallgato elem : hallgatok){
                if(elem.getAvg() < min){
                    min = elem.getAvg();
                    hallgato = elem;
                }
            }
            return hallgato;
       }else{
            return null;
       }
    }
    
    public ArrayList<Hallgato> getOsztondijasok(){
        if(!hallgatok.isEmpty()){
            ArrayList<Hallgato> osztondijasok = new ArrayList<>();
            for(Hallgato elem : hallgatok){
                if(elem.getAvg() >= 4){
                    osztondijasok.add(elem);
                }
            }
            return osztondijasok;
        }else{
            return null;
        }
    }
}
