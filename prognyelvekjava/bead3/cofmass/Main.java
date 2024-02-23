package bead3.cofmass;
import java.util.ArrayList;
import bead3.point3d.Point;

public class Main {
    public static void main (String [] args){
        int pontokSzama = 0;
        try{
            if(args.length != 1){
                throw new IllegalArgumentException();
            }else{
                pontokSzama = Integer.parseInt(args[0]);
            }
        }catch(Exception exc){
            System.out.println("Nincs bemenet, adja meg a pontok szamat!");
            pontokSzama = Integer.parseInt(System.console().readLine());
        }

        ArrayList<Point> pontok = new ArrayList<>();

        for(int i=0; i<pontokSzama; i++){
            System.out.println("Kerem adja meg a pont sulyat!");
            double mass = Integer.parseInt(System.console().readLine());
            System.out.println("Kerem adja meg a pont x koordinatajat!");
            double x = Integer.parseInt(System.console().readLine());
            System.out.println("Kerem adja meg a pont y koordinatajat!");
            double y = Integer.parseInt(System.console().readLine()); 
            System.out.println("Kerem adja meg a pont z koordinatajat!");
            double z = Integer.parseInt(System.console().readLine());
            pontok.add(new Point(x,y,z,mass));
        }

        System.out.println(Point.tomegkozeppont(pontok).toString());
        System.out.println(Point.tomegtolLegtavolabbi(pontok).toString());


    }
}