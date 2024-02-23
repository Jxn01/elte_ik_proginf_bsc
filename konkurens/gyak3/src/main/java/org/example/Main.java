package org.example;

import java.util.ArrayList;

public class Main {

    static long l = 0;

    public static void main(String[] args) throws InterruptedException {
        var threads = new ArrayList<Thread>();

        for(int i = 0; i < 10; i++){
            var t = new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    increment();
                }
            });
            threads.add(t);
        }

        threads.forEach(Thread::start);
        for(var t : threads)
            t.join();
        System.out.println(getCounter());
    }

    public synchronized static void increment(){
        l++;
    }

    public synchronized static long getCounter(){
        return l;
    }
}

