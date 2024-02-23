package ora9;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Collections;

public class Main {
    public static void main (String [] args){
        Creature creature1 = new Creature(100);
        Animal animal1 = new Animal(4,100);
        Dog dog1 = new Dog(true, 4, 10);
        Cat cat1 = new Cat(true,4,5);
        

        if(dog1 instanceof Dog){
            System.out.println("The dog1 is a Dog.");
        }else{
            System.out.println("The dog1 is NOT a Dog.");
        }

        if(dog1 instanceof Dog){
            System.out.println("The dog1 is a Dog.");
        }else{
            System.out.println("The dog1 is NOT a Dog.");
        }

        if(dog1 instanceof Animal){
            System.out.println("The dog1 is an Animal.");
        }else{
            System.out.println("The dog1 is NOT an Animal.");
        }

        if(dog1 instanceof Creature){
            System.out.println("The dog1 is a Creature.");
        }else{
            System.out.println("The dog1 is NOT a Creature.");
        }

        if(animal1 instanceof Dog){
            System.out.println("The animal1 is a Dog.");
        }else{
            System.out.println("The animal1 is NOT a Dog.");
        }

        if(animal1 instanceof Cat){
            System.out.println("The animal1 is a Cat.");
        }else{
            System.out.println("The animal1 is NOT a Cat.");
        }

        if(creature1 instanceof Dog){
            System.out.println("The animal1 is a Dog.");
        }else{
            System.out.println("The animal1 is NOT a Dog.");
        }

        if(creature1 instanceof Cat){
            System.out.println("The animal1 is a Cat.");
        }else{
            System.out.println("The animal1 is NOT a Cat.");
        }

        if(cat1 instanceof Animal){
            System.out.println("The cat1 is an Animal.");
        }else{
            System.out.println("The cat1 is NOT an Animal.");
        }

        if(cat1 instanceof Creature){
            System.out.println("The cat1 is a Creature.");
        }else{
            System.out.println("The cat1 is NOT a Creature.");
        }

        if(animal1 instanceof Dog){
            System.out.println("The animal1 is a Dog.");
        }else{
            System.out.println("The animal1 is NOT a Dog.");
        }

        LinkedList<Integer> c = new LinkedList<>();

        c = divisors(100);

        System.out.println("1. eleje");
        for(Integer elem : c){
            System.out.println(elem);
        }
        System.out.println("1. vege");



        ArrayList<String> a = new ArrayList<>();
        ArrayList<String> b = new ArrayList<>();
        a.add("");
        a.add(null);
        a.add("egy");
        a.add("epe");
        a.add("apa");

        b.addAll(a);

        a = getStrSameBeginningAndEnding(a);

        System.out.println("2a. eleje");
        for(String elem: a){
            System.out.println(elem);
        }
        System.out.println("2a. vege");

        removeStrDifferBeginningAndEnding(b);

        System.out.println("2b. eleje");
        for(String elem: b){
            System.out.println(elem);
        }
        System.out.println("2b. vege");

        ArrayList<Integer> ints = new ArrayList<>();
        ints.add(4);
        ints.add(65);
        ints.add(45);
        ints.add(44);
        ints.add(76);
        ints.add(2);
        ints.add(98);
        ints.add(72);

        System.out.println("3. eleje");
        for(int elem: ints){
            System.out.print(elem+" ");
        }
        System.out.println();

        minToFront(ints);

        for(int elem: ints){
            System.out.print(elem+" ");
        }
        System.out.println();
        System.out.println("3. vege");

    }

    static LinkedList<Integer> divisors(int a){
        LinkedList<Integer> list = new LinkedList<>();
        for(int i=1; i<=a;i++){
            if(a%i==0){
                list.add(i);
            }
        }
    
        return list;
    }
    
    static ArrayList<String> getStrSameBeginningAndEnding(ArrayList<String> inputStrings){
        ArrayList<String> result = new ArrayList<>();
    
        for(String elem : inputStrings){
            if(elem != null && !elem.equals("")){
                if(elem.charAt(0) == elem.charAt(elem.length()-1)){
                    result.add(elem);
                }
            }
        }
    
        return result;
    }
    
    static void removeStrDifferBeginningAndEnding(ArrayList<String> inputStrings){
        inputStrings.removeIf(elem -> ((elem==null || elem.equals(""))) || elem.charAt(0) != elem.charAt(elem.length()-1));
    }

    static void minToFront(ArrayList<Integer> inputArray){
        if(inputArray == null){
            throw new IllegalArgumentException("Input array can't be NULL!");
        }

        int min = Collections.min(inputArray);
        ArrayList<Integer> temp = new ArrayList<>();
        inputArray.remove(inputArray.indexOf(min));
        temp.add(min);
        temp.addAll(inputArray);
        inputArray.clear();
        inputArray.addAll(temp);
    }
}



class Creature{
    int age;

    public Creature(int age) {
        this.age = age;
    }

}

class Animal extends Creature{
    int legsNum;

    public Animal(int legsNum, int age) {
        super(age);
        this.legsNum = legsNum;
    }

}

class Dog extends Animal{
    boolean hasToyBone;

    public Dog(boolean hasToyBone, int legsNum, int age) {
        super(legsNum, age);
        this.hasToyBone = hasToyBone;
    }

}

class Cat extends Animal{
    boolean hasToyFish;

    public Cat(boolean hasToyFish, int legsNum, int age) {
        super(legsNum, age);
        this.hasToyFish = hasToyFish;
    }
}

class Human extends Creature{
    String name;

    public Human(String name, int age) {
        super(age);
        this.name = name;
    }

}

