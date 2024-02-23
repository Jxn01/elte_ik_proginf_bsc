package hu.elte.haladojava;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.concurrent.*;

public class ForkJoinExample {

    public static void main(String[] args) {
        List<Integer> integers = IntStream.range(0, 40).boxed().collect(Collectors.toList());

        // TODO sum all integers using ForkJoinPool/RecursiveTask
        SumTask task = new SumTask(integers);
        time(() -> System.out.println(task.compute()));
    }


    private static void time(Runnable runnable){
        long start = System.currentTimeMillis();
        runnable.run();
        long end = System.currentTimeMillis();
        System.out.println("Time: " + (end - start));
    }

    static class SumTask extends RecursiveTask<Long>{

        private static final int THRESHOLD = 1000;
        private final List<Integer> numbers;

        SumTask(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        protected Long compute(){

            if(numbers.size() < THRESHOLD){
                return numbers.stream().mapToLong(i -> i).sum();
            }

            List<Integer> leftList = numbers.subList(0, numbers.size() / 2);
            List<Integer> rightList = numbers.subList(numbers.size() / 2, numbers.size());

            ForkJoinTask<Long> leftTask = new SumTask(leftList).fork();
            ForkJoinTask<Long> rightTask = new SumTask(rightList).fork();

            return leftTask.join() + rightTask.join();
        }
    }
}
