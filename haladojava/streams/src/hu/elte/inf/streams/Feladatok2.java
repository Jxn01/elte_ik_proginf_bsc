package hu.elte.inf.streams;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Feladatok2 {
	public static void main(String[] args) throws IOException{
		Scanner sc = new Scanner(Feladatok2.class.getResourceAsStream("input.txt"));
		Supplier<String> mySupplier = ()->{
			if(sc.hasNextLine()) {
				return sc.nextLine();
			}
			return null;
		};
		//Stream.generate(mySupplier).filter(s->s!=null).forEach(System.out::println);
		Stream.generate(mySupplier).takeWhile(s->s!=null).forEach(System.out::println);
		System.out.println("Itt a vege");
	}

}