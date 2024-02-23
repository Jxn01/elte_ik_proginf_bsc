package hu.elte.haladojava.services;

import java.util.List;

// just for sake of an example
public interface Database {

  void connect();

  List<Person> findPersonsWithName(String name);

  void disconnect();
}
