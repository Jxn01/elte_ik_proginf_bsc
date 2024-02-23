package ora7;

import java.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

//changed naming from C# style to Java style

enum Weapon {
    FEJSZE, KETELUFEJSZE, KALAPACS, POROLY, TAGLO
}

enum Color {
    RESET("\033[0m"), // removed redundant comments
    BLACK("\033[0;30m"), RED("\033[0;31m"), GREEN("\033[0;32m"), YELLOW("\033[0;33m"), BLUE("\033[0;34m"),
    MAGENTA("\033[0;35m"), CYAN("\033[0;36m"), WHITE("\033[0;37m");

    private final String code;

    Color(String code) {
        this.code = code;
    }

    @Override // added @override
    public String toString() {
        return code;
    }
}

class Creature { // changed Unknown to Creature
    private static int ID = 1; // moved vitality to class Orc
    public int id;

    public Creature() {
        this.id = Creature.ID++;
    }

}

class Orc extends Creature {

    public String name;
    private int vitality; // change to private
    private int nameChangesRemaining; // added
    private final int damage; // changed to private final
    private int shield; // changed to private
    public Weapon weapon;

    public Orc(String name, int vitality, Weapon weapon, int damage, int shield) {
        super();

        if (vitality > 100) { // added testing for vitality interval
            throw new IllegalArgumentException("Vitality can't be more than 100!");
        }

        if (damage < 20 || damage >= 50) { // added testing for damage interval
            throw new IllegalArgumentException("Damage must be between 20 and 50 (can't be 50)!");
        }

        if (shield < 10 || shield > 20) { // added testing for shield interval
            throw new IllegalArgumentException("Shield must be between 10 and 20!");
        }

        this.name = name;
        this.nameChangesRemaining = 3;
        this.vitality = vitality;
        this.damage = damage;
        this.shield = shield;
        this.weapon = weapon;
    }

    public int getVitality(){
        return this.vitality;
    }

    public void nameChange(String newName) {
        if (newName.equals(null)) {
            throw new IllegalArgumentException("The new name can't be null!");
        }

        if (name.equals(newName)) {
            throw new IllegalArgumentException("The new name cannot be the same as the current!");
        }

        if (newName.length() < 3) {
            throw new IllegalArgumentException("The new name's length must be at least 3 characters!");
        }

        if (!newName.contains(" ")) {
            throw new IllegalArgumentException("The new name must contain a space!");
        }

        if (this.nameChangesRemaining == 0) {
            throw new IllegalArgumentException("You can't change this name anymore!");
        }

        this.name = newName;
        this.nameChangesRemaining--;
    }

    public boolean died() {
        return vitality == 0;
    }

    public void damaged(int damage) { // translated to english
        if (this.vitality - damage + this.shield > 0)
            this.vitality -= (damage + this.shield);
        else
            this.vitality = 0;
    }

    public void attack(Orc enemy) throws Exception {
        {
            if (enemy.died())
                throw new Exception("Everything has a limit ...");
            else
                enemy.damaged(this.damage);
        }
    }

    @Override
    public String toString() {
        return String.format("ID: " + this.id + "\nname: " + this.name + "\nvitality: " + this.vitality + "\nweapon: "
                + this.weapon + "\ndamage: " + this.damage + "\nshield: " + this.shield + "\ndied: " + this.died())
                + "\n";

    }
}

class Horde {
    String name;

    public Horde(String name) {
        this.orcs = new ArrayList<Orc>();
        this.name = name;
    }

    public ArrayList<Orc> orcs;

    public void addOrk(String name, int vitality, Weapon weapon, int shield, int damage) {
        this.orcs.add(new Orc(name, vitality, weapon, shield, damage));
    }

    public ArrayList<Orc> diedOrcs() throws Exception {

        if (orcs.isEmpty())
            throw new Exception("The horde is empty...");

        ArrayList<Orc> temp = new ArrayList<Orc>();
        for (Orc orc : orcs) {
            if (orc.died())
                temp.add(orc);
        }

        return temp;
    }

    public Orc strongestOrc() { //added
        Orc strongest = orcs.get(0);
        int max = orcs.get(0).weapon.ordinal();

        for (Orc elem : orcs) {
            if (max < elem.weapon.ordinal()) {
                max = elem.weapon.ordinal();
                strongest = elem;
            }
        }
        return strongest;
    }

    public ArrayList<Orc> weakOrcs(int vitality){ //added
        ArrayList<Orc> weakOrcsList = new ArrayList<>();

        for(Orc elem : orcs){
            if(elem.getVitality() < vitality){
                weakOrcsList.add(elem);
            }
        }

        return weakOrcsList;
    }

    public ArrayList<Orc> orcsWithGivenWeapon(Weapon weapon){ //added
        ArrayList<Orc> orcsWithGivenWeaponList = new ArrayList<>();

        for(Orc elem : orcs){
            if(elem.weapon.name().equals(weapon.name())){
                orcsWithGivenWeaponList.add(elem);
            }
        }

        return orcsWithGivenWeaponList;
    }

}

public class App {

    static void ReadFile(String filename, Horde horde) { // translated to english
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(";"); // translated to english
                horde.addOrk(data[0], Integer.parseInt(data[1]), Enum.valueOf(Weapon.class, data[2]),
                        Integer.parseInt(data[3]), Integer.parseInt(data[4]));
            }

            br.close();
        } catch (IOException e) {
            System.out.println("IO error");
        }
    }

    public static void main(String[] args) throws Exception {
        Horde horde = new Horde("Slaughter");
        ReadFile("OrcFile.csv", horde);

        System.out.println(horde.name);

        for (Orc orc : horde.orcs)
            System.out.println(orc.toString());

        System.out.println(Color.RED);
        System.out.println("The names of the dead orcs\n");

        for (Orc orc : horde.diedOrcs())
            System.out.println(orc.name);

        System.out.println(Color.RESET);

        System.out.println("The strongest orc is: "+horde.strongestOrc().name);

        ArrayList<Orc> weaks = horde.weakOrcs(10);
        System.out.println("\nThe weaks are:\n");
        for(Orc elem : weaks){
            System.out.println(elem.name);
        }

        System.out.println("\nThe orcs with FEJSZE are:\n");
        ArrayList<Orc> orcsWithFejsze = horde.orcsWithGivenWeapon(Weapon.FEJSZE);
        for(Orc elem : orcsWithFejsze){
            System.out.println(elem.name);
        }
    }

}