package hu.elte.haladojava.stream;

import java.util.function.IntUnaryOperator;
import java.util.stream.IntStream;

public class ParallelStreamDemonstration {

  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    IntStream
        .iterate(0, i -> ++i) // make sure it is not `i++` but `++i`!
        //.parallel() // uncomment this line!
        .limit(5)
        .map(verySlooooowIntOperator())
        .forEach(System.out::println);

    System.out.println("took " + (System.currentTimeMillis() - start) + " ms");
  }

  private static IntUnaryOperator verySlooooowIntOperator() {
    return i -> {
      try {
        Thread.sleep(2000); // ~2s
      } catch (InterruptedException ignore) {
      }
      return i * 2;
    };
  }
}
