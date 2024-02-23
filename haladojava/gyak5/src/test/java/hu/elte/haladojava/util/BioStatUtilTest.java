package hu.elte.haladojava.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.elte.haladojava.util.BioStatUtil.Gender;

public class BioStatUtilTest {

  private InputStream inputStream;

  // well it is not the best solution, we should close the returning InputStream (preferably in BioStatUtil with Stream.onClose)
  @Before
  public void setUp() throws IOException {
    String resourceName = "/biostats.csv";
    URL resource = BioStatUtil.class.getResource(resourceName);
    inputStream = resource.openStream();
  }

  @Test
  public void testCountOlderThan_40() throws IOException {
    int actualCount = BioStatUtil.countOlderThan(40, inputStream);

    Assert.assertEquals(4, actualCount);
  }

  @Test
  public void testCountOlderThan_100() throws IOException {
    int actualCount = BioStatUtil.countOlderThan(100, inputStream);
    
    Assert.assertEquals(0, actualCount);
  }

  @Test
  public void testAverageHeight_female() throws IOException {
    double averageHeight = BioStatUtil.averageHeight(Gender.F, inputStream);

    Assert.assertEquals(65.57, averageHeight, 0.01);
  }
}
