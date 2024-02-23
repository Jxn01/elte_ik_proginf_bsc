package hu.elte.haladojava.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class BioStatUtilCountOlderThanTest {

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] { { 20, 18 }, { 40, 4 }, { 100, 0 } });
  }

  private int inputAge;
  private int expectedCount;

  public BioStatUtilCountOlderThanTest(int inputAge, int expectedCount) {
    this.inputAge = inputAge;
    this.expectedCount = expectedCount;
  }

  @Test
  public void testCountOlderThan() throws IOException {
    int actualCount = BioStatUtil.countOlderThan(inputAge, openTestInputStream());
    
    Assert.assertEquals(expectedCount, actualCount);
  }

  // well it is not the best solution, we should close the returning InputStream
  // (preferably in BioStatUtil with Stream.onClose)
  private InputStream openTestInputStream() throws IOException {
    String resourceName = "/biostats.csv";
    URL resource = BioStatUtil.class.getResource(resourceName);
    return resource.openStream();
  }
}
