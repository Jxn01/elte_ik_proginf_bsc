package bead7.bar;

public class Bar {
    public static void main (String [] args){
        Beverage kubu = new Beverage("kubu", 1);
        Beverage tatra = new Beverage("tatra", 18);
        Adult adult = new Adult("Joska", 45);
        Minor minor = new Minor("Pistike", 10);
        
        if(Bartender.order(kubu, minor)){
            System.out.println(minor.name+" megihatja a "+kubu.getName()+"-t.");
        }else{
            System.out.println(minor.name+" NEM ihatja meg a "+kubu.getName()+"-t.");
        }

        if(Bartender.order(tatra, minor)){
            System.out.println(minor.name+" megihatja a "+tatra.getName()+"-t.");
        }else{
            System.out.println(minor.name+" NEM ihatja meg a "+tatra.getName()+"-t.");
        }

        if(Bartender.order(kubu, adult)){
            System.out.println(adult.name+" megihatja a "+kubu.getName()+"-t.");
        }else{
            System.out.println(adult.name+" NEM ihatja meg a "+kubu.getName()+"-t.");
        }

        if(Bartender.order(tatra, adult)){
            System.out.println(adult.name+" megihatja a "+tatra.getName()+"-t.");
        }else{
            System.out.println(adult.name+" NEM ihatja meg a "+tatra.getName()+"-t.");
        }
    }
}

class Bartender{
    public static boolean order(Beverage beverage, Guest guest){
        if(beverage.getLegalAge() == 18 && !(guest instanceof Adult)){
            return false;
        }else{
            return true;
        }
    }
}

class Guest{
    protected String name;
    protected int age;

    public Guest(String name, int age) {
        if(name.equals(null)){
            throw new IllegalArgumentException("Name can't be null!");
        }

        if(name.equals("")){
            throw new IllegalArgumentException("Name can't be empty!");
        }

        if(age > 100 || age < 0){
            throw new IllegalArgumentException("Age must be between 0 and 100!");
        }

        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }
}

class Adult extends Guest{
    public Adult(String name, int age){
        super(name, age);
    }

}

class Minor extends Guest{
    public Minor(String name, int age){
        super(name, age);
    }
}

class Beverage{
    private String name;
    private int legalAge;

    public Beverage(String name, int legalAge) {
        if(name.equals(null)){
            throw new IllegalArgumentException("Name can't be null!");
        }

        if(name.equals("")){
            throw new IllegalArgumentException("Name can't be empty!");
        }

        if(legalAge > 100 || legalAge < 0){
            throw new IllegalArgumentException("Legal age must be between 0 and 100!");
        }

        this.legalAge = legalAge;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getLegalAge() {
        return this.legalAge;
    }
}
