package hu.elte.haladojava.services;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;
import org.mockito.Mockito;

public class StatisticsServiceTest {

  private static final String BELA = "Bela";
  private static final String JOZSI = "Jozsi";

  @Test
  public void testCountPersonsWithName_matchingName() {
    // arrange
    Database database = Mockito.mock(Database.class);
    StatisticsService serviceUnderTest = new StatisticsService(database);
    Mockito.when(database.findPersonsWithName("Bela")).thenReturn(Arrays.asList(new Person("Bela")));

    // act
    int countOfBelas = serviceUnderTest.countPersonsWithName(BELA);

    // assert
    Assertions.assertEquals(1, countOfBelas);
  }

  @Test
  public void testCountPersonsWithName_mismatchingName() {
    // arrange
    Database database = Mockito.mock(Database.class);
    StatisticsService serviceUnderTest = new StatisticsService(database);
    Mockito.when(database.findPersonsWithName("Bela")).thenReturn(new ArrayList<>());
    
    // act
    int countOfJozsis = serviceUnderTest.countPersonsWithName(JOZSI);
    
    // assert
    Assertions.assertEquals(0, countOfJozsis);
  }

  @Test
  public void testCountPersonsWithName_connectionHandling() {
    // arrange
    Database database = Mockito.mock(Database.class);
    StatisticsService serviceUnderTest = new StatisticsService(database);
    
    // act
    serviceUnderTest.countPersonsWithName("dummy");
    
    // assert
    Mockito.verify(database).connect();
    Mockito.verify(database).disconnect();
  }

  @Test
  public void testCountPersonsWithName_noInsert() {
    // arrange
    Database database = Mockito.mock(Database.class);
    StatisticsService serviceUnderTest = new StatisticsService(database);
    
    // act
    serviceUnderTest.countPersonsWithName("dummy");
    
    // assert
    Mockito.verify(database, Mockito.never()).insertPerson(Mockito.any());
  }

  @Test
  public void testCountPersonsWithName_disconnect_alwaysCalled() {
    // arrange
    IllegalStateException exceptionThrownByDatabase = new IllegalStateException();
    Database database = Mockito.mock(Database.class);
    Mockito.when(database.findPersonsWithName(Mockito.any())).thenThrow(exceptionThrownByDatabase);
    StatisticsService serviceUnderTest = new StatisticsService(database);

    // act
    Assertions.assertThrows(IllegalStateException.class, () -> serviceUnderTest.countPersonsWithName("dummy"));

    // assert
    Mockito.verify(database).disconnect();
  }

  @Test
  public void testCountPersonsWithName_operationsInOrder() {
    // arrange
    Database database = Mockito.mock(Database.class);
    InOrder inOrder = Mockito.inOrder(database);
    StatisticsService serviceUnderTest = new StatisticsService(database);

    // act
    serviceUnderTest.countPersonsWithName("dummy");

    // assert
    inOrder.verify(database).connect();
    inOrder.verify(database).findPersonsWithName(Mockito.any());
    inOrder.verify(database).disconnect();
  }

  @Test
  public void testCountPersonsWithName_noOperationsAfterDisconnect() {
    // arrange
    Database database = Mockito.mock(Database.class);
    InOrder inOrder = Mockito.inOrder(database);
    StatisticsService serviceUnderTest = new StatisticsService(database);

    // act
    serviceUnderTest.countPersonsWithName("dummy");

    // assert
    inOrder.verify(database).connect();
    inOrder.verify(database).findPersonsWithName(Mockito.any());
    inOrder.verify(database).disconnect();
    Mockito.verifyNoMoreInteractions(database);
  }
}
