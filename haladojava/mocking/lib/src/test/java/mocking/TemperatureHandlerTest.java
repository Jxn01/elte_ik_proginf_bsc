package mocking;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;

public class TemperatureHandlerTest {
	
	@ParameterizedTest
	@CsvSource({"false", "true"})
	public void temperatureHandlerRetunfStatusOfThermometer(boolean state) {
		TemperatureHandler th = new TemperatureHandler(new ThermometerDouble(state));
		assertEquals(state, th.isOn());
	}
	
	@ParameterizedTest
	@CsvSource({"5,41", "2,35.6", "-1,30.2"})
	public void measurementIsReturned(int c, double f) {
		GeneralThermometer t = mock(GeneralThermometer.class);
		when(t.measure()).thenReturn(c);
		TemperatureHandler th = new TemperatureHandler(t);
		
		
		//assertEquals(f, th.measure());
		assertTrue(Math.abs(f-th.measure())<0.00001);
	}
	
	@Test
	public void restartWaitsUntilThermometerIsOn() {
		GeneralThermometer mock = mock(GeneralThermometer.class);
		when(mock.isOn()).thenReturn(false, false, false, true);
		TemperatureHandler th = new TemperatureHandler(mock);
		
		th.restart();
		
		verify(mock, atLeast(3)).isOn();
		
	}
	
	@Test
	public void spyOnList() {
		List<Integer> myList = spy(new ArrayList<>());
		when(myList.addAll(ArgumentMatchers.anyCollection())).thenReturn(true);
		
		myList.add(1);
		myList.add(2);
		myList.addAll(List.of(3,4,5));
		
		assertAll(()->assertEquals(2, myList.size()), ()->verify(myList).add(1));
	}
	
	@Test
	public void rightValueIsForwarded() {
		GeneralThermometer gt = mock(GeneralThermometer.class);
		ArgumentCaptor<Integer> myCaptor = ArgumentCaptor.forClass(Integer.class);
		doNothing().when(gt).calibrate(myCaptor.capture());
		
		TemperatureHandler th = new TemperatureHandler(gt);
		th.calibrate(10);
		
		assertEquals(10, myCaptor.getValue());
	}
	
	@Test
	public void bdd() {
		//GIVEN
		List<Integer> myList = mock(List.class);
		BDDMockito.given(myList.add(ArgumentMatchers.anyInt())).willReturn(true);
		
		//WHEN
		myList.add(8);
		
		//THEN
		BDDMockito.then(myList).should().add(8);
	}
	
	private static class ThermometerDouble extends Thermometer {
		
		private final boolean state;
		private int setCalibaration;

		public ThermometerDouble(boolean state) {
			this.state = state;
		}

		@Override
		public int measure() {
			return 5;
		}

		@Override
		public void restart() {
		}

		@Override
		public void calibrate(int x) {
			setCalibaration = x;
		}

		@Override
		public boolean isOn() {
			return state;
		}
		
	}

}
