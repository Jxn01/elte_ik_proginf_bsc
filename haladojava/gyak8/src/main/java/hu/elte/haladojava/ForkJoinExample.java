package hu.elte.haladojava;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ForkJoinExample {

    public static void main(String[] args) {
        List<Integer> integers = IntStream.range(0, 100).boxed().collect(Collectors.toList());
        // TODO sum all integers using ForkJoinPool/RecursiveTask
        SumTask task = new SumTask(integers);
    }

    static class SumTask {

        private static final int THRESHOLD = 1000;
        private final List<Integer> numbers;

        SumTask(List<Integer> numbers) {
            this.numbers = numbers;
        }

        @Override
        protected long compute(){
            if(numbers.size() < THRESHOLD){
                return numbers.stream().mapToLong(i -> i).sum();
            }
            int middle = numbers.size() / 2;
            SumTask left = new SumTask(numbers.subList(0, middle));
            SumTask right = new SumTask(numbers.subList(middle, numbers.size()));
            left.fork();
            long rightResult = right.compute();
            long leftResult = left.join();
            return leftResult + rightResult;
        }
    }
}
