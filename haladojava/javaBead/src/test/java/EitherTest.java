import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import pst8ra.javabead.Either;

import java.util.NoSuchElementException;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class EitherTest {

    @Test
    public void leftTest() {
        Either<String, Integer> either = Either.left("Hello");
        assertTrue(either.isLeft());
        assertFalse(either.isRight());
        assertEquals("Hello", either.getLeft());
    }

    @Test
    public void rightTest() {
        Either<String, Integer> either = Either.right(10);
        assertFalse(either.isLeft());
        assertTrue(either.isRight());
        assertEquals(10, (int) either.getRight());
    }

    @Test
    public void iterateRightTest() {
        Either<Integer, Integer> either = Either.right(10);
        assertEquals(10, (int) Either.iterate(either, 0, x -> x + 1));
        assertEquals(11, (int) Either.iterate(either, 1, x -> x + 1));
        assertEquals(12, (int) Either.iterate(either, 2, x -> x + 1));
    }

    @Test
    public void iterateLeftTest() {
        Either<Integer, Integer> either = Either.left(10);
        assertEquals(10, (int) Either.iterate(either, 0, x -> x + 1));
        assertEquals(10, (int) Either.iterate(either, 1, x -> x + 1));
        assertEquals(10, (int) Either.iterate(either, 2, x -> x + 1));
    }

    @Test
    public void isLeftTest() {
        Either<Integer, Integer> either = Either.left(10);
        assertTrue(either.isLeft());
        assertFalse(either.isRight());
    }

    public static Stream<Arguments> eitherProvider() {
        return Stream.of(
                Arguments.of(Either.right(10)),
                Arguments.of(Either.right("Hello")),
                Arguments.of(Either.right(10.0)),
                Arguments.of(Either.right('c')));
    }

    @ParameterizedTest
    @MethodSource("eitherProvider")
    public <L, R> void isRightTest(Either<L, R> either) {
        assertFalse(either.isLeft());
        assertTrue(either.isRight());
    }

    @Test
    public void getLeftTest() {
        Either<Integer, Integer> either = Either.left(10);
        assertEquals(10, (int) either.getLeft());
    }

    @Test
    public void getRightTest() {
        Either<Integer, Integer> either = Either.right(10);
        assertEquals(10, (int) either.getRight());
    }

    @Test
    public void swapTest() {
        Either<Integer, Integer> either = Either.left(10);
        assertTrue(either.swap().isRight());
        assertEquals(10, (int) either.swap().getRight());
    }

    @Test
    public void mapLeftTest() {
        Either<Integer, String> either = Either.left(10);
        assertEquals(10, (int) either.map(x -> x + 'c').getLeft());
        assertThrows(NoSuchElementException.class, () -> either.map(x -> x + 'c').getRight());
    }

    @Test
    public void mapRightTest() {
        Either<Boolean, Integer> either = Either.right(10);
        assertEquals("10c", either.map(x -> x + "c").getRight());
    }

    @Test
    public void bindLeftTest() {
        Either<Integer, Integer> either = Either.left(10);
        assertEquals(10, either.bind(x -> Either.left(x + 1)).getLeft());
    }

    @Test
    public void bindRightTest() {
        Either<Integer, Integer> either = Either.right(10);
        assertEquals("alma", either.bind(x -> Either.right("alma")).getRight());
    }
}
