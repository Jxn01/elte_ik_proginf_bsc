/*
import java.util.ArrayList;
import java.util.List;

public class DataMover {
    public static final int DEFAULT_WAIT_TIME = 123;
    public static final int[] DEFAULT_THREAD_WAIT_TIMES = {111, 256, 404};
    private static int ARRAY_SIZE = 3;
    private int[] data;
    private List<Thread> movers;

    public static void main(String[] args) {
        int waitTime;
        int[] threadWaitTimes;

        if (args.length == 0) {
            waitTime = DEFAULT_WAIT_TIME;
            threadWaitTimes = DEFAULT_THREAD_WAIT_TIMES;
        } else {
            ARRAY_SIZE = args.length-1;
            waitTime = Integer.parseInt(args[0]);
            threadWaitTimes = new int[args.length - 1];
            for (int i = 1; i < args.length; i++) {
                threadWaitTimes[i - 1] = Integer.parseInt(args[i]);
            }
        }

        DataMover dataMover = new DataMover();
        dataMover.startThreads(waitTime, threadWaitTimes);

        dataMover.waitForThreads();

        System.out.println("Final array contents: " + dataMover.arrayToString());
    }
    public DataMover() {
        data = new int[ARRAY_SIZE];
        movers = new ArrayList<>();
        initializeArray();
    }

    private void initializeArray() {
        for (int i = 0; i < ARRAY_SIZE; i++) {
            data[i] = i * 1000;
        }
    }

    public void startThreads(int waitTime, int[] threadWaitTimes) {
        for (int i = 0; i < threadWaitTimes.length; i++) {
            Thread thread = new Thread(new Mover(i, waitTime, threadWaitTimes[i]));
            movers.add(thread);
            thread.start();
        }
    }

    public void waitForThreads() {
        for (Thread mover : movers) {
            try {
                mover.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String arrayToString() {
        StringBuilder result = new StringBuilder("[");
        for (int i = 0; i < ARRAY_SIZE; i++) {
            result.append(data[i]);
            if (i < ARRAY_SIZE - 1) {
                result.append(", ");
            }
        }
        result.append("]");
        return result.toString();
    }

    private class Mover implements Runnable {
        private final int index;
        private final int waitTime;
        private final int threadWaitTime;

        public Mover(int index, int waitTime, int threadWaitTime) {
            this.index = index;
            this.waitTime = waitTime;
            this.threadWaitTime = threadWaitTime;
        }

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                wait(index * threadWaitTime);

                synchronized (data) {
                    int oldValue = data[index];
                    System.out.println("#" + index + ": data " + index + " == " + oldValue);
                    data[index] = oldValue - index;
                }

                wait(waitTime);

                int nextIndex = (index + 1) % ARRAY_SIZE;
                synchronized (data) {
                    int newValue = data[nextIndex] + index;
                    System.out.println("#" + index + ": data " + index + " -> " + nextIndex + " (" + newValue + ")");
                    data[nextIndex] = newValue;
                }
            }
        }

        private void wait(int milliseconds) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
*/

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class DataMover2 implements Callable<DataMover2.DataMover2Result> {
    public static final int DEFAULT_N = 4;
    public static final int[] DEFAULT_TASK_WAIT_TIMES = {111, 256, 404, 123};
    public static int[] taskWaitTimes;
    public static AtomicInteger arrivalCount = new AtomicInteger(0);
    public static AtomicInteger totalSent = new AtomicInteger(0);
    public static AtomicInteger totalArrived = new AtomicInteger(0);
    public static ExecutorService pool;
    public static List<BlockingQueue<Integer>> queues;
    public static List<Future<DataMover2Result>> moverResults;
    public static List<Integer> discards;
    private final int taskIndex;

    public static void main(String[] args) {
        int n = (args.length > 0) ? args.length : DEFAULT_N;
        taskWaitTimes = (args.length > 0) ? Arrays.stream(args).toList().stream().mapToInt(Integer::parseInt).toArray() : DEFAULT_TASK_WAIT_TIMES;

        pool = Executors.newFixedThreadPool(Math.min(n, 100));
        queues = new ArrayList<>(n);
        moverResults = new ArrayList<>(n);
        discards = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            queues.add(new LinkedBlockingQueue<>());
        }

        for (int i = 0; i < n; i++) {
            DataMover2 task = new DataMover2(i);
            moverResults.add(pool.submit(task));
        }

        pool.shutdown();

        try {
            pool.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {e.printStackTrace();}

        for (Future<DataMover2Result> future : moverResults) {
            try {
                DataMover2Result result = future.get();
                totalArrived.addAndGet(result.data);
                totalArrived.addAndGet(result.forwarded);
            } catch (InterruptedException | ExecutionException e) {e.printStackTrace();}
        }

        for (BlockingQueue<Integer> queue : queues) {
            discards.addAll(queue);
        }

        int totalSentValue = totalSent.get();
        int totalArrivedValue = totalArrived.get();
        int totalDiscardsValue = discards.stream().mapToInt(Integer::intValue).sum();

        System.out.println("discarded " + discards + " = " + totalDiscardsValue);
        System.out.println("sent " + totalSentValue + " === got " + totalArrivedValue + " + discarded " + totalDiscardsValue);

        if (totalSentValue == totalArrivedValue + totalDiscardsValue) {
            System.out.println("CORRECT");
        } else {
            System.out.println("WRONG");
        }
    }

    public DataMover2(int taskIndex) {
        this.taskIndex = taskIndex;
    }

    @Override
    public DataMover2Result call() {
        DataMover2Result result = new DataMover2Result();

        while (arrivalCount.get() < 5 * queues.size()) {
            int sentValue = ThreadLocalRandom.current().nextInt(10001);
            totalSent.addAndGet(sentValue);

            try {
                queues.get(taskIndex).put(sentValue);

                System.out.println("total " + arrivalCount + "/" + (5 * queues.size())
                        + " | #" + taskIndex + " sends " + sentValue);

                Thread.sleep((long) taskWaitTimes[taskIndex] * taskIndex);
                int waitTime = ThreadLocalRandom.current().nextInt(300, 1001);

                Integer receivedValue = queues.get((taskIndex + 1) % queues.size()).poll(waitTime, TimeUnit.MILLISECONDS);

                if (receivedValue == null) {
                    System.out.println("total " + arrivalCount + "/" + (5 * queues.size())
                            + " | #" + taskIndex + " got nothing...");
                    continue;
                }

                if (receivedValue % queues.size() == taskIndex) {
                    arrivalCount.incrementAndGet();
                    result.count++;
                    result.data += receivedValue;
                    System.out.println("total " + arrivalCount + "/" + (5 * queues.size())
                            + " | #" + taskIndex + " got " + receivedValue);
                } else {
                    int forwardedValue = receivedValue - 1;
                    queues.get((taskIndex + 1) % queues.size()).put(forwardedValue);
                    result.forwarded++;
                    System.out.println("total " + arrivalCount + "/" + (5 * queues.size())
                            + " | #" + taskIndex + " forwards " + forwardedValue + " ["
                            + ((taskIndex + 1) % queues.size()) + "]");
                }

            } catch (InterruptedException e) {e.printStackTrace();}
        }

        return result;
    }

    public static class DataMover2Result {
        public int count;
        public int data;
        public int forwarded;

        public DataMover2Result() {
            this.count = 0;
            this.data = 0;
            this.forwarded = 0;
        }
    }
}
