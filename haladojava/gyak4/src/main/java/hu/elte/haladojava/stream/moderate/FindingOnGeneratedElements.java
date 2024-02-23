package hu.elte.haladojava.stream.moderate;

import java.util.List;
import java.util.Optional;

public class FindingOnGeneratedElements {

  public Optional<Integer> findFirstPrime(List<Integer> numbers) {
    return numbers.stream().filter(this::isPrime).findFirst();
  }

  private boolean isPrime(int n) {
    for (int i = 2; i <= n / 2; i++) {
      if (n % i == 0) {
        return false;
      }
    }
    return true;
  }
}
