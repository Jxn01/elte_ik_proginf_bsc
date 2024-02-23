package bead4.Person;

import java.util.Objects;

public class Person {
    public enum Gender{
        MALE, FEMALE;
    }

    String firstName;
    String lastName;
    String occupation;
    Gender gender;
    int birthYear;

    public Person() {
    }

    public Person(String firstName, String lastName, String occupation, Gender gender, int birthYear) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.occupation = occupation;
        this.gender = gender;
        this.birthYear = birthYear;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Person)) {
            return false;
        }
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName) && Objects.equals(lastName, person.lastName) && Objects.equals(occupation, person.occupation) && Objects.equals(gender, person.gender) && birthYear == person.birthYear;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, occupation, gender, birthYear);
    }
}


