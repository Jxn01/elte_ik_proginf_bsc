package rf.example;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

public class Task2 {

	public static void main(String[] args) {
		printAuthor(MyClass.class);
		printAuthor(String.class);
		printDifferentAuthors(MyClass.class);
	}

	private static <T> void printAuthor(Class<T> clazz) {
		Author author = clazz.getAnnotation(Author.class);
		if (author != null) {
			System.out.println(author.name());
		} else {
			Authors authors = clazz.getAnnotation(Authors.class);
			if (authors != null) {
				StringJoiner authorJoiner = new StringJoiner(", ");
				for (Author a : authors.value()) {
					authorJoiner.add(a.name());
				}
				System.out.println("by authors: " + authorJoiner.toString());
			} else {
				System.out.println("no name author");
			}
		}
	}

	private static <T> void printDifferentAuthors(Class<T> clazz) {
		Author author = clazz.getAnnotation(Author.class);
		String classAuthorName = null;
		if (author != null) {
			classAuthorName = author.name();
		}
		for (Method m : clazz.getDeclaredMethods()) {
			Author methodAuthor = m.getAnnotation(Author.class);
			if (methodAuthor != null && !classAuthorName.equals(methodAuthor.name())) {
				System.out.println(
						m.getName() + "\twas written by: " + methodAuthor.name() + "\tand not by: " + author.name());
			} else if(methodAuthor == null) {
				Authors methodAuthors = m.getAnnotation(Authors.class);
				Set<String> methodAuthorNames = new HashSet<>();
				StringJoiner methodAuthorJoiner = new StringJoiner(", ");
				if(methodAuthors!=null) {
					for(Author a: methodAuthors.value()) {
						methodAuthorNames.add(a.name());
						methodAuthorJoiner.add(a.name());
					}
				}
				if(!methodAuthorNames.contains(classAuthorName)) {
					System.out.println(
						m.getName() + "\twas written by: " + methodAuthorJoiner.toString() + "\tand not by: " + author.name());
				}
			}
		}
	}

	@Author(name = "Balazs")
	private static class MyClass {

		@Author(name = "Balazs")
		public String writtenByMe() {
			return "hi";
		}

		@Author(name = "Elsa")
		public String writtenByElse() {
			return "bye";
		}

		@Authors({ @Author(name = "Balazs"), @Author(name = "Jozsi") })
		public String manyAuthors() {
			return "This was wiritten by many authors";
		}
		
		@Authors({ @Author(name = "Geza"), @Author(name = "Jozsi") })
		public String manyForeignAuthors() {
			return "This was wiritten by foreign many authors";
		}
	}

}
