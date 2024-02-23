package animalfarmmine;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public abstract class Animal {
    private String name;
    private int weight;
    private int VERYSLIM;
    private ArrayList<Integer> meals; // (étkezések száma, élelem mennyisége (dkg))

    public Animal(String name, int weight, ArrayList<Integer> meals) {
        this.name = name;
        this.weight = weight;
        this.meals = meals;
    }

    public Animal() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVERYSLIM() {
        return VERYSLIM;
    }

    public void setVERYSLIM(int VERYSLIM) {
        this.VERYSLIM = VERYSLIM;
    }

    public ArrayList<Integer> getMeals() {
        return meals;
    }

    public void setMeals(ArrayList<Integer> meals) {
        this.meals = meals;
    }

    @Override
    public String toString() {
        return "Animal{" + "name=" + name + ", weight=" + weight;
    }
    
    public boolean moreThanOneKg(){
        return meals.stream().reduce(0, Integer::sum) >= 100;
    }
    
    public boolean isVerySlim(){
        return weight < VERYSLIM;
    }
}
