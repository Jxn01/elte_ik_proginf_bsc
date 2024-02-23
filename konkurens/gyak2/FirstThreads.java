class SimpleThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100000; ++i) {
            System.out.print(i + ",");
        }
    }
}

class SimpleRunnable implements Runnable {
    @Override
    public void run() {
        for (int i = 1; i <= 100000; ++i) {
            System.out.print(i + "," + -i + ",");
        }
    }
}

public class FirstThreads {
    public static void main(String[] args) {
        SimpleThread simpleThread = new SimpleThread();
        SimpleRunnable simpleRunnable = new SimpleRunnable();
        simpleThread.start();
        new Thread(simpleRunnable).start();
    }
}