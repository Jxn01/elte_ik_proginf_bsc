package szalak;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class SzalakMain {
	public static void main(String[] args) {
		AtomicInteger counter = new AtomicInteger(0);
		Runnable printer = () -> {
			try {
				System.out.println(Thread.currentThread().getName() + " is running: " + counter.getAndIncrement());
			} catch (Exception e) {
				e.printStackTrace();
				Thread.currentThread().interrupt();
			}
		};
		/*
		for (int i = 0; i < 100; ++i) {
			Thread th = new Thread(printer);
			th.setDaemon(true);
			th.start();
		}
		*/
		var threadPool = Executors.newFixedThreadPool(20);
		for(int i=0; i<100; ++i) {
			threadPool.submit(printer);
		}
		printer.run();
		List<Integer> nums = IntStream.iterate(0, i->i+1).limit(10_000).boxed().toList();
		List<Future<Boolean>> futures = new ArrayList<>();
		List<Callable<Boolean>> theTasks = new ArrayList<>();
		for(int i=0; i<nums.size()/100; ++i) {
			int start = i*100;
			int end = (i+1)*100;
			Callable<Boolean> task = ()->{
				for(int j = start; j<end; ++j) {
					if(nums.get(j) == 5_555) {
						return true;
					}
				}
				throw new NoSuchElementException();
				//return false;
			};
			Future<Boolean> result = threadPool.submit(task);
			futures.add(result);
			theTasks.add(task);
		}
		int failCounter = 0;
		for(var f : futures) {
			try {
				if(f.get()) {
					System.out.println("5555 is found");
				} else {
					++failCounter;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e2) {
				//e2.printStackTrace();
			}
		}
		threadPool.shutdown();
		System.out.println("fails: "+failCounter);
		ForkJoinPool fjp = ForkJoinPool.commonPool();
		RecursiveAction ra = new MyAction(nums, 0, nums.size());
		fjp.invoke(ra);
		boolean found = fjp.invoke(new MyTask(nums, 0, nums.size()));
		System.out.println("found it: "+found);
	}
	
	private static class MyAction extends RecursiveAction {
		private static final long serialVersionUID = 5399572841468911410L;
		private final List<Integer> nums;
		private final int start;
		private final int end;
		
		public MyAction(List<Integer> nums, int start, int end) {
			this.nums = nums;
			this.start = start;
			this.end = end;
		}
		
		@Override
		protected void compute() {
			if(end-start<=100) {
				for(int i=start; i<end; ++i) {
					if(nums.get(i) == 5_555) {
						System.out.println("\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!!!! found 5555 between: "+start+" -- "+end+"\n\n");
						return;
					}
				}
				System.err.println("not found between: "+start+" -- "+end);
			} else {
				int middle = (start+end)/2;
				var task1 = new MyAction(nums, start, middle).fork();
				var task2 = new MyAction(nums, middle, end).fork();
				System.err.println("Too big: "+start+" -- "+end);
				task1.join();
				task2.join();
			}
		}
		
	}
	
	private static class MyTask extends RecursiveTask<Boolean> {
		private final List<Integer> nums;
		private final int start;
		private final int end;
		public MyTask(List<Integer> nums, int start, int end) {
			super();
			this.nums = nums;
			this.start = start;
			this.end = end;
		}
		@Override
		protected Boolean compute() {
			if(end-start<=100) {
				for(int i=start; i<end; ++i) {
					if(nums.get(i) == 5_555) {
						return true;
					}
				}
				return false;
			} else {
				int middle = (start+end)/2;
				var task1 = new MyTask(nums, start, middle).fork();
				var task2 = new MyTask(nums, middle, end).fork();
				return task1.join() || task2.join();
			}
		}
	}
}
