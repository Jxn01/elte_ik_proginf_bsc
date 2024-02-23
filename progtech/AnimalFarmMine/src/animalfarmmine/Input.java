package animalfarmmine;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author jxn
 */
public class Input {
    private final ArrayList<Animal> animals;

    public Input(ArrayList<Animal> Animals) {
        this.animals = Animals;
    }
    
    public void read(String fileName) throws FileNotFoundException, InvalidInputException{
        Scanner sc = new Scanner(new BufferedReader(new FileReader(fileName)));
        int nOfLines = sc.nextInt();
        while(sc.hasNext()){
            Animal animal = switch(sc.next()){
                case "E" -> new Emu();
                case "K" -> new Goat();
                case "T" -> new Cow();
                case "L" -> new Horse();
                default  -> throw new InvalidInputException("Bad input, animal type expected!");
            };
            animal.setName(sc.next());
            ArrayList<Integer> meals = new ArrayList<>();
            for(int i = 0; i < sc.nextInt(); i++){
                meals.add(sc.nextInt());
            }
            animal.setMeals(meals);
            animals.add(animal);
        }
    }
    
    public void report(){
        System.out.println("Kórosan sovány állatok: ");
        animals.forEach(e -> e.isVerySlim()); //unfinished
    }
}
