package hu.elte.haladojava.gyak2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class BioStatUtilTest {

    @Test
    public void testCountOlderThan_40() throws IOException {
        String resourceName = "/biostats.csv";
        URL resource = BioStatUtil.class.getResource(resourceName);
        InputStream inputStream = resource.openStream();

        int actualCount = BioStatUtil.countOlderThan(40, inputStream);

        Assertions.assertEquals(4, actualCount);
    }

    @Test
    public void testCountOlderThan_100() throws IOException {
        String resourceName = "/biostats.csv";
        URL resource = BioStatUtil.class.getResource(resourceName);
        InputStream inputStream = resource.openStream();

        int actualCount = BioStatUtil.countOlderThan(100, inputStream);

        Assertions.assertEquals(0, actualCount);
    }

    @Test
    public void testAverageHeight_female() throws IOException {
        String resourceName = "/biostats.csv";
        URL resource = BioStatUtil.class.getResource(resourceName);
        InputStream inputStream = resource.openStream();

        double averageHeight = BioStatUtil.averageHeight(BioStatUtil.Gender.F, inputStream);

        Assertions.assertEquals(65.57, averageHeight, 0.01);
    }
}
