package hu.inf.elte.hj;

import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntConsumer;

public class MyLambdas {

	public static void main(String[] args) {
		IntIntFunction f = (int i) -> 2 * i;
		System.out.println(f.plus2(3));
		System.out.println(f.apply(5));
		System.out.println(f.apply(7));
		System.out.println("-------------------");
		Function<Integer, Integer> g = (i) -> i * 2;
		Function<Integer, Integer> h = (i) -> i + 2;
		Function<Integer, Integer> j = g.andThen(h);
		System.out.println(j.apply(3));
		IntIntFunction c = f.plus3(f);

		int[] it = new int[] { 0 };
		Supplier<Integer> oneSupplier = () -> {
			it[0] += 2;
			System.out.println("inner: " + it[0]);
			return 0;
		};

		Supplier<Integer> zeroSuplier = MyLambdas::zero;

		for (int i = 0; i < 10; ++i) {
			System.out.println(i + "\t" + oneSupplier.get());
		}
		System.out.println("----------------");
		Supplier<Integer> primes = primes();
		for (int i = 0; i < 10; ++i) {
			System.out.println(primes.get());
		}
		
		Runnable r = ()->System.out.println("Hello World");
		
		r.run();
		
		Function<Integer, Boolean> mod2Equals0 = (i)->i%2==0;
		
		System.out.println(mod2Equals0.apply(1));
		System.out.println(mod2Equals0.apply(2));
		
		Predicate<Integer> isEven = (i)->i%2==0;
		System.out.println(isEven.test(1));
		System.out.println(isEven.test(2));
		
		Predicate<Integer> isOdd = (i)->!isEven.test(i);
		System.out.println(isOdd.test(1));
		System.out.println(isOdd.test(2));
		
		Predicate<Integer> alwaysTrue = isOdd.or(isEven);
		System.out.println(alwaysTrue.test(1) +"\t"+alwaysTrue.test(2));
		
		System.out.println(deni(i->isEven.test(i), 1));
		
		System.out.println("-------------");
		BiFunction<Integer, Double, Boolean> isEqual = (i, d) -> {
			double di = i;
			//return Double.compare(di, d) == 0;
			return di == d;
		};
		
		System.out.println(isEqual.apply(1, 1.0));
		System.out.println(isEqual.apply(2, Double.NaN));
		
		BiFunction<Double, Double, Boolean> isDEqual = (d1,d2)->Double.compare(d1, d2) == 0;
		System.out.println(isDEqual.apply(Double.NaN, Double.NaN));
		
		int[] s = new int[] {0};
		Consumer<Integer> sum = (i)->{ s[0] += i;};
		IntConsumer myPrintln1 = System.out::println;
		Consumer<Integer> myPrintln = System.out::println;
		
		for(int i=0; i<10; ++i) {
			sum.accept(i);
		}
		
		System.out.println(s[0]);
		
		myPrintln.accept((Integer)s[0]);
		
		
		BiFunction<Function<Integer, Integer>, Function<Integer, Integer>, Predicate<Integer>> my12Lambda = (f1, f2) ->{
			return (Integer t)->{
				int a = f1.apply(t);
				int b = f2.apply(t);
				return a<b;

			};
		}; 
		
		var myPred = toPredicate((Integer i)->i*2, (Integer jj)->jj*3);
		System.out.println(myPred.test(13));
	}

	private static Integer zero() {
		return 0;
	}

	private static Supplier<Integer> primes() {
		int[] prime = new int[] { 1 };
		return () -> {
			while (true) {
				++prime[0];
				if (prime[0] == 2) {
					return 2;
				}
				int sqrt = (int) Math.sqrt(prime[0]);
				boolean breakingCondition = false;
				for (int i = 3; i <= sqrt && !breakingCondition; i+=2) {
					if (prime[0] % i == 0) {
						breakingCondition = true;
					}
				}
				if (!breakingCondition) {
					return prime[0];
				}
			}
		};
	}
	
	private static boolean deni(Predicate<Integer> p, int i) {
		return !p.test(i);
	}
	
	private static <T> Predicate<T> toPredicate (Function<T, Integer> f1, Function<T, Integer> f2) {
		return (T t)->{
			int a = f1.apply(t);
			int b = f2.apply(t);
			return a<b;
		};
	}
}
