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
