package hu.elte.haladojava.regex;

import java.util.regex.Pattern;

public class RegexExample2 {
    public static void main(String[] args) {
        String input = "34345345 + 45345345";
        String regex = "(\\d{1,10})\\s*([+\\-/*])\\s(\\d{1,10})";

        System.out.println(input.replace('3', '9'));
        System.out.println(input.replace("34", ""));

        System.out.println(input.replaceAll(regex, "($1, $3)$2"));

        /* Pattern.compile("\\s+").splitAsStream(input).forEach(System.out::println); */
    }
}
