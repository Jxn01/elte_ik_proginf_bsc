package hu.elte.haladojava.stream.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;


public class NaturalStreamSortingTest {

  private static final List<String> NAMES = Arrays.asList("John", "Susan", "Anna", "Tom", "Michail");

  @Test
  public void testSortNamesAlphabetically() {
    List<String> expectedList = Arrays.asList("Anna", "John", "Michail", "Susan", "Tom");

    List<String> actualList = new NaturalStreamSorting().sortNamesAlphabetically(NAMES);

    Assertions.assertEquals(expectedList, actualList);
  }
}
