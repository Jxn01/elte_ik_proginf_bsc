package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Main {

    private static List<Integer> ints = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) throws InterruptedException {
        var t1 = new Thread(() -> {
            for(int i = 0; i < 100; i++){
                ints.add(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        var t2 = new Thread(() -> {
            synchronized (ints) {
                ints.stream().iterator().forEachRemaining((i) -> {
                    System.out.println(i);
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        });

        t1.start();
        Thread.sleep(500);
        t2.start();
        t1.join();
        t2.join();
    }

    public static void main2(String[] args) throws InterruptedException {
        var t1 = new Thread(() -> {
            for(int i = 0; i < 100; i++){
                ints.add(i);
            }
        });

        var t2 = new Thread(() -> {
            for(int i = 0; i < 100; i++){
                ints.add(i);
            }
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(ints.size());
    }
}