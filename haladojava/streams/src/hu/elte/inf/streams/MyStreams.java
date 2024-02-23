package hu.elte.inf.streams;

import java.util.ArrayList;
import java.util.List;

public class MyStreams {

	public static void main(String[] args) {
		List<Integer> integers = List.of(1, 2, 3, 4);
		integers.stream().filter((i) -> i.intValue() % 2 == 0).peek(i -> {
			System.out.println("i jelenlegi erteke: " + i);
		}).map(i -> i + 10).forEach(i -> System.out.println(i));
		List<Integer> it2 = new ArrayList<>(integers);
		var iter = it2.iterator();
		while (iter.hasNext()) {
			int i = iter.next();
			if (i % 2 != 0) {
				iter.remove();
			}
		}
		for (int idx = 0; idx < it2.size(); ++idx) {
			it2.set(idx, it2.get(idx) + 10);
		}
		System.out.println(it2);
		System.out.println(integers);
		var myStream = integers.stream().filter(i -> i < 3).map(i -> i * 5)
				.peek(i -> System.out.println("i eppen: " + i));
		System.out.println("After creating myStream");
		var vegeredmeny = myStream.toList();
		System.out.println(vegeredmeny);
	}

}
