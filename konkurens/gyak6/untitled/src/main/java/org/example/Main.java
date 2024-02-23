package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        var numbers = Collections.synchronizedList(Files.lines(Path.of("numbers.txt"))
                .mapToInt(Integer::parseInt)
                .);

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        var result = Collections.synchronizedList(new ArrayList<Integer>());

        List<Future<String>> tasks = new ArrayList<>();
        for(int n : numbers){
            Future
        }


        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdownNow();

        result.forEach(System.out::println);
    }

    public static boolean isPrime(int num){
        if(num < 2){
            return false;
        }
        for(int i = 2; i < num; i++){
            if(num % i == 0){
                return false;
            }
        }
        return true;
    }


    public static void main2(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        var result = Collections.synchronizedList(new ArrayList<String>());

        Future<?> task = executorService.submit(() -> {
            System.out.println("...");
            result.add("x");
        });

        task.get();

        Future<String> task2 = executorService.submit(() -> {
            System.out.println("...");
            return "x";
        });

        System.out.println(task2.get());

        executorService.shutdown();
        executorService.awaitTermination(5, TimeUnit.SECONDS);
        executorService.shutdownNow();

        System.out.println("Program vége!");
    }
}