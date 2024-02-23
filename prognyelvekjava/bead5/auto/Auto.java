package bead5.auto;
import bead5.auto.utils.Color;

public class Auto {
    static int db = 0;
    String rendszam;
    Color szin;
    int maxSebesseg;

    public Auto(String rendszam, Color szin, int maxSebesseg) {
        this.rendszam = rendszam;
        this.szin = szin;
        this.maxSebesseg = maxSebesseg;
        db++;
    }

    public Auto() {
        this.rendszam = "AAA-000";
        this.szin = Color.BLUE;
        this.maxSebesseg = 120;
    }

    public static Auto gyorsabb(Auto a, Auto b){
        if(a == null || b == null){
            throw new IllegalArgumentException("Parameter can't be null!");
        }
        if(a.maxSebesseg >= b.maxSebesseg){
            return a;
        }else{
            return b;
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d, rendszam: %s, szin: %s, maximum sebesseg: %d", db, rendszam, szin.name(), maxSebesseg);
    }

}
