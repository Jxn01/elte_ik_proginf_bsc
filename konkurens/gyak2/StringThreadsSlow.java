class PrintStringThread extends Thread {
    private static void fakePrintLn(String s) {
        for (int i = 0; i < s.length(); ++i) {
            System.out.print(s.charAt(i));
        }
        System.out.println();
    }

    private String string;

    PrintStringThread(String s) {
        string = s;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 100000; ++i) {
            fakePrintLn(string + " " + i);
        }
    }
}

public class StringThreadsSlow {
    public static void main(String[] args) {
        String[] array = {"hello", "world", "other"};
        for (String s: array) {
            PrintStringThread pst = new PrintStringThread(s);
            pst.start();
        }
    }
}