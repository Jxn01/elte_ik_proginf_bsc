package hu.elte.haladojava.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class NanoUnit {

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> testClass = Class.forName(args[0]);
        System.out.println("testing class " + testClass + "...");
        Object testClassInstance = testClass.getConstructor().newInstance();

        for (Method m : testClass.getMethods()) {
            TestCase testAnnotation = m.getAnnotation(TestCase.class);
            if (testAnnotation != null) {
                testMethod(m, testClassInstance, testAnnotation);
            }
        }
    }

    private static void testMethod(Method m, Object testClassInstance, TestCase annot) {
        try {
            m.invoke(testClassInstance);
            System.out.println(m.getName() + " passed");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            Throwable targetException = e.getTargetException();
            handleException(m, annot, targetException);
        }
    }

    private static void handleException(Method m, TestCase annot, Throwable throwable) {
        Class<? extends Throwable> expectedExceptionClass = annot.expected();
        if (expectedExceptionClass.equals(NoException.class)) {
            System.err.println(m.getName() + " failed because " + throwable.getMessage());
        } else {
            if (expectedExceptionClass.isInstance(throwable)) {
                System.out.println(m.getName() + " passed, " + expectedExceptionClass + " was thrown");
            } else {
                System.err.println(m.getName() + " failed because " + expectedExceptionClass + " was expected but was not thrown");
            }
        }
    }
}
