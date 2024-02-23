package szalak;

import java.util.ArrayList;
import java.util.List;

public class ThreadsInJava21 {

	public static void main(String[] args) throws InterruptedException {
		Runnable task = () -> {
			System.out.println(Thread.currentThread().getName() + " is runnning");
		};
		Thread th = Thread.ofPlatform().daemon().unstarted(task);
		th.start();
		th.join();
		th = Thread.ofVirtual().unstarted(task);
		th.start();
		th.join();
		List<Thread> threads = new ArrayList<>();
		for (int i = 0; i < 100_000; ++i) {
			int id = i;
			threads.add(Thread.ofVirtual().start(() -> {
				try {
					Thread.sleep(1_000);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName() + " id: " + id);
			}));
		}
		for(Thread t: threads) {
			t.join();
		}
	}
}
