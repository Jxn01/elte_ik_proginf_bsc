package hu.elte.haladojava.reflection;

public class Asserts {

    public static void assertTrue(boolean value) {
        if (!value) {
            throw new AssertionError("expected true but was false");
        }
    }

    public static void assertFalse(boolean value) {
        if (value) {
            throw new AssertionError("expected false but was true");
        }
    }

    public static void assertEquals(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError(expected + " was expected to be equal to " + actual + " but was not");
        }
    }
}
