package generics;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

public class Genetic {
	
	private static interface Entity<T> {
		Entity<T> cross(Entity<T> a, Entity<T> b);
		void mutate();
		double fittness();
	}
	
	private static class Animal implements Entity<Animal> {
		double weight;
		double strength;
		
		public Animal(double weight, double strength) {
			this.weight=weight;
			this.strength=strength;
		}

		@Override
		public Animal cross(Entity<Animal> a, Entity<Animal> b) {
			Animal from1 = (Animal) a;
			Animal from2 = (Animal) b;
			return new Animal((from1.weight+from2.weight)/2.0, (from1.strength+from2.strength)/2.0);
		}

		@Override
		public void mutate() {
			Random r = new Random();
			double diff = 1.0 + (r.nextDouble(0.2) -0.1); 
			if(r.nextBoolean()) {
				weight *= diff;
			} else {
				strength *= diff;
			}
		}

		@Override
		public double fittness() {
			return strength/weight;
		}
		
		@Override
		public String toString() {
			return ("[weight: "+weight+",\tstrength: "+strength+",\tfitness: "+fittness()+"]");
		}
	}
	
	private static <T> void geneticAlg(Class<? extends Entity<T>> type, int popCount, int crossOverCount, int pruneCount, Supplier<Entity<T>> createRandomEntity) {
		Random r = new Random();
		List<Entity<T>> elements = new ArrayList<>();
		for(int i=0; i<popCount; ++i) {
			elements.add(createRandomEntity.get());
		}
		for(int cc = 0; cc<crossOverCount; ++cc) {
			List<Entity<T>> newGen = new ArrayList<>();
			for(int i=0; i<elements.size()*10; ++i) {
			int a = r.nextInt(elements.size());
			int b = r.nextInt(elements.size());
				newGen.add(elements.get(a).cross(elements.get(a), elements.get(b)));
			}
			newGen.sort(Comparator.<Entity<T>>comparingDouble(e->e.fittness()).reversed());
			newGen = new ArrayList<>(newGen.stream().limit(pruneCount).toList());
			while(newGen.size()<popCount) {
				newGen.add(createRandomEntity.get());
			}
			for(var e: newGen) {
				if(r.nextDouble()<0.1) {
					e.mutate();
				}
			}
			elements = newGen;
		}
		System.out.println(elements);
	}
	
	public static void main(String[] args) {
		Random r = new Random();
		geneticAlg(Animal.class, 20, 30, 10, ()->new Animal(100*r.nextDouble(), r.nextDouble()));
	}
	
	
}
