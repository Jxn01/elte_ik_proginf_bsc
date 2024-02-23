package javaapplication1;

/**
 *
 * @author jxn
 */
public class JavaApplication1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Person p = new Person("Peter", 10);
        People people = new People();
        people.addPerson(p);
    }
    
}
