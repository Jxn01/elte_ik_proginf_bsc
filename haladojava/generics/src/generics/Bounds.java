package generics;

import java.util.ArrayList;
import java.util.List;

public class Bounds {
	
	public static void main(String[] args) {
		List<PositiveNumber> pos = new ArrayList<>();
		pos.add(new PositiveNumber(1));
		List<NaturalNumber> nat = new ArrayList<>();
		nat.add(new NaturalNumber(-1));
		List<RealNumber> rel = new ArrayList<>();
		merger(pos, nat, rel);
		System.out.println(rel);
	}
	
	private static <T> void merger(List<? extends T> a, List<? extends T> b, List<? super T> target) {
		target.addAll(a);
		for(T bElement : b) {
			target.add(bElement);
		}
	}
	
	
	private static class RealNumber {
		Number n;
		
		public RealNumber(Number n) {
			this.n=n;
		}
		
		@Override
		public String toString() {
			return "("+n.doubleValue()+")";
		}
	}
	
	private static class NaturalNumber extends RealNumber {
		public NaturalNumber(Integer i) {
			super(i);
		}
	}
	
	private static class PositiveNumber extends NaturalNumber {
		public PositiveNumber(int i) {
			super(check(i));
		}
		
		private static int check(int i) {
			if(i<1) {
				throw new IllegalArgumentException();
			} 
			return i;
		}
	}

}
