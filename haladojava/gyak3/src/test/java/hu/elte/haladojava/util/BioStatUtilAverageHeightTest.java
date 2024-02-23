package hu.elte.haladojava.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import hu.elte.haladojava.util.BioStatUtil.Gender;

public class BioStatUtilAverageHeightTest {

  private InputStream inputStream;

  // TODO
  public void testAverageHeight(Gender inputGender, double expectedAverageHeight) {
    double averageHeight = BioStatUtil.averageHeight(inputGender, inputStream);

    // TODO
  }

  // well it is not the best solution, we should close the returning InputStream
  // (preferably in BioStatUtil with Stream.onClose)
  @BeforeEach
  public void setUp() throws IOException {
    String resourceName = "/biostats.csv";
    URL resource = BioStatUtil.class.getResource(resourceName);
    inputStream = resource.openStream();
  }
}
