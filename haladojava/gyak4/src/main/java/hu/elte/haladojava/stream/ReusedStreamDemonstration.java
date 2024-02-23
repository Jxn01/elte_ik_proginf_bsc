package hu.elte.haladojava.stream;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ReusedStreamDemonstration {

  public static void main(String[] args) {
    IntStream positiveNumbers = IntStream.iterate(1, i -> ++i);

    System.out.println("first 10 prime numbers: " +
          positiveNumbers
            .filter(ReusedStreamDemonstration::isPrime)
            .limit(10) // without limit it would already be an infinite stream
            .mapToObj(Integer::toString)
            .collect(Collectors.joining(", ")));

    // what do we expect?
    System.out.println("first 10 even numbers: " +
          positiveNumbers
            .filter(n -> n % 2 == 0)
            .limit(10)
            .mapToObj(Integer::toString)
            .collect(Collectors.joining(", ")));
  }

  private static boolean isPrime(int n) {
    if (n < 2) {
      return false;
    }
    for (int i = 2; i <= n / 2; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
}
