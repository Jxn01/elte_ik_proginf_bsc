package hu.elte.haladojava.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// we are now just pretending we use a database...
public class DatabaseImpl implements Database {

  private final List<Person> persons = new ArrayList<>();

  public DatabaseImpl() {
    persons.add(new Person("Bela"));
    persons.add(new Person("Magdianyus"));
    persons.add(new Person("Bela2"));
    persons.add(new Person("bela"));
  }

  @Override
  public void connect() {
    // whatever, no database, so nothing to do
  }

  @Override
  public List<Person> findPersonsWithName(String name) {
    return persons.stream().filter(p -> p.getName().equals(name)).collect(Collectors.toList());
  }

  @Override
  public void insertPerson(Person person) {
    persons.add(person);
  }

  @Override
  public void disconnect() {
    // whatever, no database, so nothing to do
  }

}
