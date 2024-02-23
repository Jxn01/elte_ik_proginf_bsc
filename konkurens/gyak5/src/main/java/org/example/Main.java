package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.nio.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {/*
    public static void main(String[] args) throws InterruptedException{
        SynchronizedArrayStack<Integer> stack = SynchronizedArrayStack.synchronizedArrayStack(new ArrayStack<>());
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                stack.push(i);
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10_000; i++) {
                stack.push(i);
            }
        });
        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        int stackSize = 0;
        while (!stack.isEmpty()) {
            stack.pop();
            stackSize++;
        }
        System.out.println("stack size is " + stackSize);
    }
    */

    public static void main(String[] args) throws InterruptedException{
        int cores = Runtime.getRuntime().availableProcessors();
        List<Number> numbers = new ArrayList<>();

        ExecutorService executorService = Executors.newFixedThreadPool(cores);

        var threads = new ArrayList<Thread>();
        for (var number : numbers) {
            threads.add(new Thread(() -> {

            }));
        }

        threads.forEach(Thread::start);
        for(var t : threads){
            t.join();
        }
    }
    public boolean isPrime(int num){
        if(num < 2){
            return false;
        }

        for(int i = 2; i < num; i++){
            if(num % i == 0){
                return false;
            }
            if(i * i > num){
                break;
            }
        }
        return true;
    }
}

class SynchronizedArrayStack<T> implements Stack<T> {
    private final Stack<T> stack;

    public static <T> SynchronizedArrayStack<T> synchronizedArrayStack(Stack<T> stack){
        return new SynchronizedArrayStack<T>(stack);
    }

    private SynchronizedArrayStack(Stack<T> stack) {
        this.stack = stack;
    }

    @Override
    public synchronized void push(T element) {
        stack.push(element);
    }

    @Override
    public synchronized T pop() {
        return stack.pop();
    }

    @Override
    public synchronized T top() {
        return stack.top();
    }

    @Override
    public synchronized boolean isEmpty() {
        return stack.isEmpty();
    }
}