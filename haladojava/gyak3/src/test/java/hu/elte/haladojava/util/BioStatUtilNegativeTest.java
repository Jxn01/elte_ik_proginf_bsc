package hu.elte.haladojava.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;

import hu.elte.haladojava.util.BioStatUtil.Gender;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// normally this is not separated
public class BioStatUtilNegativeTest {

  // TODO
  public void testAverageHeight_emptyStream(Gender inputGender) {
    InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
    // TODO
  }
}
