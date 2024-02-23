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

import hu.elte.haladojava.util.BioStatUtil.Gender;

@RunWith(Parameterized.class)
public class BioStatUtilAverageHeight {

  @Parameters
  public static Collection<Object[]> data() {
    return Arrays.asList(new Object[][] { { Gender.F, 65.57 }, { Gender.M, 71.27 } });
  }

  private Gender inputGender;
  private double expectedAverageHeight;

  public BioStatUtilAverageHeight(Gender inputGender, double expectedAverageHeight) {
    this.inputGender = inputGender;
    this.expectedAverageHeight = expectedAverageHeight;
  }

  @Test
  public void testAverageHeight() throws IOException {
    double averageHeight = BioStatUtil.averageHeight(inputGender, openTestInputStream());

    Assert.assertEquals(expectedAverageHeight, averageHeight, 0.01);
  }

  // well it is not the best solution, we should close the returning InputStream
  // (preferably in BioStatUtil with Stream.onClose)
  private InputStream openTestInputStream() throws IOException {
    String resourceName = "/biostats.csv";
    URL resource = BioStatUtil.class.getResource(resourceName);
    return resource.openStream();
  }
}
