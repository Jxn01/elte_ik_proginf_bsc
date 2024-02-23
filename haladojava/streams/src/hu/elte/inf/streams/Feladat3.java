package hu.elte.inf.streams;

import java.io.File;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Stream;

public class Feladat3 {
	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(new File("Coords.txt"));
		var s = Stream.generate(()->{
			if(sc.hasNextLine()) {
				return sc.nextLine();
			}
			return null;
		}).takeWhile(Objects::nonNull).
				map(line->{
					String[] parts = line.split(",");
					double x = Double.parseDouble(parts[0]);
					double y = Double.parseDouble(parts[1]);
					return new CoordDistance(line, Math.sqrt(x*x + y*y));
				}).sorted().findFirst().get();
				/*.mapToDouble((String line)->{
			String[] parts = line.split(",");
			double x = Double.parseDouble(parts[0]);
			double y = Double.parseDouble(parts[1]);
			return Math.sqrt(x*x + y*y);
		}).max().getAsDouble();*/
		System.out.println(s.coords());
	}
	
	private static final record CoordDistance (String coords, double distance) implements Comparable<CoordDistance> {
		private static final Comparator<CoordDistance> COMPARATOR = Comparator.comparingDouble(CoordDistance::distance);

		@Override
		public int compareTo(CoordDistance o) {
			return COMPARATOR.compare(this, o);
		}
		
		
	} 
}
