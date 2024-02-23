package hu.elte.haladojava.services;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class StatisticsServiceTest {

  private static final String BELA = "Bela";
  private static final String JOZSI = "Jozsi";

  @Test
  public void testCountPersonsWithName_matchingName() {
    // arrange
    FakeDatabase database = new FakeDatabase();
    StatisticsService serviceUnderTest = new StatisticsService(database);

    // act
    int countOfBelas = serviceUnderTest.countPersonsWithName(BELA);

    // assert
    Assert.assertEquals(1, countOfBelas);
  }

  @Test
  public void testCountPersonsWithName_mismatchingName() {
    // arrange
    FakeDatabase database = new FakeDatabase();
    StatisticsService serviceUnderTest = new StatisticsService(database);
    
    // act
    int countOfJozsis = serviceUnderTest.countPersonsWithName(JOZSI);
    
    // assert
    Assert.assertEquals(0, countOfJozsis);
  }

  @Test
  public void testCountPersonsWithName_connectionHandling() {
    // arrange
    FakeDatabase database = new FakeDatabase();
    StatisticsService serviceUnderTest = new StatisticsService(database);
    
    // act
    serviceUnderTest.countPersonsWithName("dummy");
    
    // assert
    Assert.assertTrue(database.isConnectCalled);
    Assert.assertTrue(database.isDisconnectCalled);
  }

  // this is ugly, only for demonstration purposes, we will do it better a bit later!
  static class FakeDatabase implements Database {

    private boolean isConnectCalled;
    private boolean isDisconnectCalled;

    @Override
    public List<Person> findPersonsWithName(String name) {
      if (BELA.equals(name)) {
        return Arrays.asList(new Person(BELA));
      } else {
        return Arrays.asList();
      }
    }
    
    @Override
    public void disconnect() {
      isDisconnectCalled = true;
    }
    
    @Override
    public void connect() {
      isConnectCalled = true;
    }
  }  
}
