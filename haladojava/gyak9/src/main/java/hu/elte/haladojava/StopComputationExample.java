package hu.elte.haladojava;

public class StopComputationExample {

    static boolean isGameOver;

    public static void main(String[] args) throws InterruptedException {
        Thread s = new Thread(() -> {
            while (!isGameOver) {
                System.out.println("örökké élek, bibibí");
            }
        });
        s.start();

        Thread.sleep(5000);
        System.out.println("nyissz");
        isGameOver = true;
    }
}
