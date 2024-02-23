package ora3;

public class Kangaroo {
    private String name;
    private int age;

    public Kangaroo() {
    }

    public Kangaroo(String name, int age) {
        if(name.isEmpty()){
            throw new IllegalArgumentException("A név nem lehet üres!");
        }
        if(age < 0){
            throw new IllegalArgumentException("Az életkor nem lehet negatív!");
        }
        if(age > 100){
            throw new IllegalArgumentException("Az életkor nem lehet 100-nál nagyobb!");
        }
        this.name = name;
        this.age = age;
    }

    public Kangaroo(int number) {
        if(number < 0){
            throw new IllegalArgumentException("Nincsenek negatív kenguruk!");
        }
        System.out.println(number+" kenguru lábainak száma: "+number*2);
    }

    public void display(String country){
        if(country.isEmpty()){
            throw new IllegalArgumentException("Ilyen ország nincs!");
        }
        System.out.println("Name: "+name);
        System.out.println("Residence: "+country);
        System.out.println("Age: "+(++age));
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "{" +
            " name='" + getName() + "'" +
            ", age='" + getAge() + "'" +
            "}";
    }

    public static void main (String [] args){
        
    }
}
