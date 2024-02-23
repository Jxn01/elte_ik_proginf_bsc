package bead5;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import bead5.auto.Auto;
import bead5.auto.utils.Color;

public class Main {
    public static void main (String [] args){
        ArrayList<Auto> autok = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader("input.txt"));
            String line="";
            while((line = br.readLine()) != null){
                String[] adatok = line.split(",");
                autok.add(new Auto(adatok[0], Color.valueOf(adatok[1]), Integer.parseInt(adatok[2])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Auto.gyorsabb(autok.get(0), autok.get(1)).toString());

    }
}
