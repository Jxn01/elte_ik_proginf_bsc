package rf.example;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.stream.Stream;

public class Task1 {
	
	public static void main(String[] args) {
		System.out.println(isAllPrivate(RosszLeszarmazott.class, false));
		System.out.println(isAllPrivate(JoLeszarmazott.class, false));
	}

	
	public static <T> boolean isAllPrivate(Class<T> clazz, boolean simple) {
		if(simple) {
			return clazz.getFields().length == 0;
		}
		Class<?> c = clazz;
		while(c.getSuperclass() != null) {
			if(!Arrays.stream(c.getDeclaredFields()).allMatch(f->Modifier.isPrivate(f.getModifiers()))) {
				return false;
			}
			c = c.getSuperclass();
		}
		return true;
	}
	
	private static class RosszOs {
		private int i;
		public int j;
	}
	
	private static class JoOs {
		private int i;
		private int j;
	}
	
	private static class RosszLeszarmazott extends RosszOs {
		
	}
	
	private static class JoLeszarmazott extends JoOs {
		
	}
}
