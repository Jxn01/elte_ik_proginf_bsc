package hu.elte.haladojava.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface TestCase {

    Class<? extends Throwable> expected() default NoException.class;

}

class NoException extends Throwable {

}