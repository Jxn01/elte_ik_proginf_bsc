package hu.elte.haladojava.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

public class BioStatUtil {

  private BioStatUtil() {
    // utility class
  }

  public enum Gender {
    F, M;
  }

  public static int countOlderThan(int age, InputStream input) {
    return 0; // TODO
  }

  public static double averageHeight(Gender gender, InputStream input) {
    return 0; // TODO
  }

  private static Stream<Person> read(InputStream input) {
    BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
    return reader.lines()
        .skip(1) // header
        .map(Person::parse);
  }

  private static class Person {
    final String name;
    final Gender gender;
    final int age;
    final int height;
    final int weight;

    Person(String name, Gender gender, int age, int height, int weight) {
      this.name = name;
      this.gender = gender;
      this.age = age;
      this.height = height;
      this.weight = weight;
    }

    static Person parse(String line) {
      String[] elements = line.split(",");
      String name = elements[0].trim();
      Gender gender = Gender.valueOf(elements[1].trim().replaceAll("\"", ""));
      int age = Integer.parseInt(elements[2].trim());
      int height = Integer.parseInt(elements[3].trim());
      int weight = Integer.parseInt(elements[4].trim());
      return new Person(name, gender, age, height, weight);
    }

    public String getName() {
      return name;
    }

    public Gender getGender() {
      return gender;
    }

    public int getAge() {
      return age;
    }

    public int getHeight() {
      return height;
    }

    public int getWeight() {
      return weight;
    }
  }
}
