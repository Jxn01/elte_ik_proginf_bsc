package javaapplication1;

import java.util.ArrayList;

/**
 *
 * @author jxn
 */
public class People {
    private ArrayList<Person> people;

    public People() {
        people = new ArrayList<>();
    }
    
    

    public People(ArrayList<Person> people) {
        this.people = people;
    }
    
    /**
     * 
     * Adds a new person 
     */
    public void addPerson(Person p){
        people.add(p);
    }
    
    /**
     * Removes a person.
     * @param p
     * @return true if the person was in the list 
     */
    
    public boolean removePerson(Person p){
        return people.remove(p);
    }
}
