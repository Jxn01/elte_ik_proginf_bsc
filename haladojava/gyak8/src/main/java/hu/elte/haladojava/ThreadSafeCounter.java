package hu.elte.haladojava;

import java.util.concurrent.*;

public class ThreadSafeCounter {

    private static int counter;

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        Runnable increaseCounter1000Times = () -> {
            for (int i = 0; i < 1000; i++) {
                counter++;
            }
        };

        for (int i = 0; i < 1000; i++) {
            executorService.execute(increaseCounter1000Times);
        }

        System.out.println(counter);
    }
}
