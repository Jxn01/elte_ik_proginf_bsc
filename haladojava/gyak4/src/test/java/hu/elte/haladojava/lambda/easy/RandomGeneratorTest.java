package hu.elte.haladojava.lambda.easy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.IntSupplier;
import java.util.stream.IntStream;

public class RandomGeneratorTest {

  @Test
  public void testCreateRandomGenerator() {
    IntSupplier randomGenerator = new RandomGenerator().createRandomGenerator(5, 10);

    Assertions.assertTrue(IntStream.generate(randomGenerator).limit(100).allMatch(i -> i >= 5 && i < 10));

    // this is not the best test, could potentially break but highly unlikely
    Assertions.assertTrue(IntStream.generate(randomGenerator).limit(100).distinct().count() > 1);
  }
}
