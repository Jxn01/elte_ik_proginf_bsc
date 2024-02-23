package hu.elte.haladojava.generic;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Method;
import java.util.Arrays;

public class RepeatableAnnotExample {

    @Author(name = "x")
    @Author(name = "y")
    @Author(name = "z")
    public static void main(String[] args) {
        Method mainMethod = Arrays.stream(RepeatableAnnotExample.class.getMethods())
                .filter(m -> "main".equals(m.getName()))
                .findFirst().get();

        System.out.println(mainMethod.getAnnotations()[0]);
        System.out.println(mainMethod.getAnnotation(Authors.class));


        Object o = 1;

        if (o instanceof Integer) {
            System.out.println(o);
        }
    }

}

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Authors.class)
@interface Author {
    String name();
}

@Retention(RetentionPolicy.RUNTIME)
@interface Authors {
    Author[] value();
}