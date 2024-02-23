class PrintStringThread extends Thread {
    private String string;

    PrintStringThread(String s) {
        string = s;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 100000; ++i) {
            System.out.print(string + " " + i + ", ");
        }
    }
}

public class StringThreads {
    public static void main(String[] args) {
        String[] array = {"hello", "world", "other"};
        for (String s: array) {
            PrintStringThread pst = new PrintStringThread(s);
            pst.start();
        }
    }
}