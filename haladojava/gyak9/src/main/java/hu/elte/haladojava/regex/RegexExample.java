package hu.elte.haladojava.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExample {
    public static void main(String[] args) {
        Pattern phoneNumberRegex = Pattern.compile("\\+(36)([237]0)(\\d{7})");
        test("+36301112233", phoneNumberRegex);
        test("+36201112233", phoneNumberRegex);
        test("+36701112233", phoneNumberRegex);
        test("+3670112233", phoneNumberRegex);
        test("+36101112233", phoneNumberRegex);
        // escapes: \\s \\d \\w

    }

    private static void test(String phoneNumber, Pattern regex){
        Matcher matcher = regex.matcher(phoneNumber);
        if(matcher.matches()){
            System.out.println("Valid phone number: " + phoneNumber);
            System.out.println("Country code: " + matcher.group(1));
            System.out.println("Area code: " + matcher.group(2));
            System.out.println("Subscriber number: " + matcher.group(3));
        } else {
            System.out.println("Invalid phone number: " + phoneNumber);
        }
    }
}
