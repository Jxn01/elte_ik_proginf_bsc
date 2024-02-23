package hu.elte.inf;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

public class PointTest {
	private static final int Y = 3;
	private static final int X = 2;
	private Point point;

	@BeforeEach
	public void init() {
		point = new Point(X, Y);
	}

	@Test
	public void constructorSetsCoordinates() {
		Assertions.assertAll(List.of(() -> Assertions.assertEquals(X, point.getX(), "X coordinate should be set to 2"),
				() -> Assertions.assertEquals(Y, point.getY(), "Y is not set correctly")));
	}

	@ParameterizedTest
	@CsvSource({ "true,2,3,should match", "false,3,3,Should not match", "false,2,4,Should not match(y)" })
	public void testEquals(boolean equals, int x, int y, String msg) {
		Assertions.assertEquals(equals, point.equals(new Point(x, y)), msg);
	}

	@Test
	public void differentCalssIsNotEquals() {
		Assertions.assertFalse(point.equals(Boolean.FALSE));
	}

	@Test
	public void nullIsNotEqual() {
		Assertions.assertFalse(point.equals(null));
	}

	@Test
	public void sameObjectIsEqual() {
		Assertions.assertTrue(point.equals(point));
	}

	@Test
	public void toStringIsRight() {
		assertEquals("Point [x=2, y=3]", point.toString());
	}

	@Test
	public void hashCodeOfSameCoordsAreTheSame() {
		assertEquals(point.hashCode(), new Point(X, Y).hashCode());
	}

	@ParameterizedTest
	@MethodSource("constructroDoesNotAllowParamsLessThenZero")
	public void constructroDoesNotAllowParamsLessThenZero(int x, int y) {
		assertThrows(IllegalArgumentException.class, () -> new Point(x, y),
				"Constructor should not allow negativ numbers");
	}

	public static List<Arguments> constructroDoesNotAllowParamsLessThenZero() {
		return List.of(Arguments.of(0, -1), Arguments.of(-1, 0));
	}
	
	@Test
	public void samePointshasZeroDistance() {
		var p = new Point(X, Y);
		assertEquals(0.0, p.distanceFrom(point));
	}
	
	@Test
	@DisplayName("Having the same X coordinate but differnt Y returns the Y difference as distance")
	public void sameXDifferentY() {
		var p = new Point(X, Y+5);
		assertEquals(5.0, p.distanceFrom(point));
	}
	
	@Test
	@DisplayName("Having the same Y coordinate but differnt X returns the X difference as distance")
	public void sameYDifferentX() {
		var p = new Point(X+1, Y);
		assertEquals(1.0, p.distanceFrom(point));
	}
	
	@Test
	public void sameXLessY() {
		var p = new Point(X, Y-1);
		assertEquals(1.0, p.distanceFrom(point));
	}
	
	@Test
	public void sameYLessX() {
		var p = new Point(X-1, Y);
		assertEquals(1.0, p.distanceFrom(point));
	}
	
	@Test
	@DisplayName("Having differnt X and Y returns the pithagoras distance")
	public void differentXDifferentY() {
		var p = new Point(X+3, Y+4);
		assertEquals(5.0, p.distanceFrom(point));
	}
}
