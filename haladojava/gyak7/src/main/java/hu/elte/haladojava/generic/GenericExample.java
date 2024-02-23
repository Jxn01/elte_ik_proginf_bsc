package hu.elte.haladojava.generic;

import java.util.ArrayList;
import java.util.List;

public class GenericExample {

    public static void main(String[] args) {
        List<? extends Number> numbers = new ArrayList<Integer>();
    }
}
