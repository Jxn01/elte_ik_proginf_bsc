package hu.elte.inf.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Feladatok {
	public static void main(String[] args) throws IOException {
		try(BufferedReader br = new BufferedReader(new InputStreamReader(Feladatok.class.getResourceAsStream("input.txt")))) {
			//br.lines().forEach(System.out::println);
			try(PrintWriter pw = new PrintWriter(new File("output.txt"))) {
				//br.lines().map(s->"java"+s).forEach(pw::println);
				//br.lines().map(s->"java"+s).filter(s->s.length()>7).forEach(pw::println);
//				br.lines().skip(3).map(s->"java"+s).filter(s->s.length()>7).forEach(pw::println);
//				int[] counter = new int[] {0};
//				br.lines().takeWhile(i->++counter[0]<=5).map(s->"java"+s).filter(s->s.length()>7).forEach(pw::println);
//				br.lines().sorted().forEach(pw::println);
//				br.lines().sorted((s1, s2) -> Integer.compare(s1.length(), s2.length())).forEach(pw::println);
				br.lines().map(s->{
					String r = reverse(s);
					if(s.equals(r)) {
						return s;
					}
					return s+r;
				}).forEach(pw::println);
			}
		}
	}
	
	private static String reverse(String s) {
		StringBuilder sb = new StringBuilder();
		sb.append(s);
		return sb.reverse().toString();
	}
}
