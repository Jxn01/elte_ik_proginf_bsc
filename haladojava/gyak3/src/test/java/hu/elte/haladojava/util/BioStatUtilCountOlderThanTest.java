package hu.elte.haladojava.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BioStatUtilCountOlderThanTest {

  // TODO
  public void testCountOlderThan(int inputAge, int expectedCount) throws IOException {
    int actualCount = BioStatUtil.countOlderThan(inputAge, openTestInputStream());

    // TODO
  }

  // well it is not the best solution, we should close the returning InputStream
  // (preferably in BioStatUtil with Stream.onClose)
  private InputStream openTestInputStream() throws IOException {
    String resourceName = "/biostats.csv";
    URL resource = BioStatUtil.class.getResource(resourceName);
    return resource.openStream();
  }
}
