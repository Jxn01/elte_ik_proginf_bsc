package hu.elte.haladojava.stream.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

public class FindingOnFiniteCollectionTest {

  private FindingOnFiniteCollection tested = new FindingOnFiniteCollection();

  @Test
  public void testFindFirstPrime() {
    Assertions.assertEquals(Optional.of(7), tested.findFirstPrime(Arrays.asList(4, 6, 7, 8, 9)));
    Assertions.assertEquals(Optional.empty(), tested.findFirstPrime(Arrays.asList(4, 6, 8, 9)));
  }

  @Test
  public void testIsAnyPrime() {
    Assertions.assertTrue(tested.isAnyPrime(Arrays.asList(4, 6, 7, 8, 9)));
    Assertions.assertTrue(tested.isAnyPrime(Arrays.asList(2)));
    Assertions.assertFalse(tested.isAnyPrime(Arrays.asList()));
    Assertions.assertFalse(tested.isAnyPrime(Arrays.asList(4, 6, 8, 9, 10)));
  }

  @Test
  public void testIsAllPrime() {
    Assertions.assertTrue(tested.isAllPrime(Arrays.asList(3, 5, 7, 11)));
    Assertions.assertTrue(tested.isAllPrime(Arrays.asList(2)));
    Assertions.assertTrue(tested.isAllPrime(Arrays.asList()));
    Assertions.assertFalse(tested.isAllPrime(Arrays.asList(3, 5, 7, 10)));
  }
}
