package bead4.Main;
import bead4.Person.*;

public class Main {
    public static void main (String [] args){
        Person person1 = new Person("Aladar", "Teszt", "Programozo", Person.Gender.MALE,  2002);
        Person person2 = new Person("Aladar", "Teszt", "Programozo", Person.Gender.MALE,  2002);
        Person person3 = new Person("Aladar", "Teszt", "Programozo", Person.Gender.MALE,  2003);
        System.out.println("Person1 equals Person2: "+person1.equals(person2));
        System.out.println("Person1 equals Person3: "+person1.equals(person3));
    }

}
