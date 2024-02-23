package rf.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Ex1 {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<Example1> clazz = Example1.class;
		Class<Example1> clazz2 = (Class<Example1>) new Example1().getClass();
		Class<Example1> clazz3 = (Class<Example1>) Class.forName("rf.example.Ex1$Example1");
		System.out.println(clazz);
		System.out.println(clazz2);
		System.out.println(clazz3);
		Class<?> c = Example2.class;
		while (c.getSuperclass() != null) {
			for (Field f : c.getDeclaredFields()) {
				System.out.println(f.getName() + "\t" + Modifier.toString(f.getModifiers()) + "\t" + f.getType());
			}
			c = c.getSuperclass();
		}
		
		Example1 ex1 = new Example2();
		ex1.k2=true;
		Class<Example1> ex1Class = (Class<Example1>) ex1.getClass();
		try {
			Field f = ex1Class.getField("k2");
			System.out.println("this is in ex1: "+f.get(ex1));
			Field f2 = ex1.getClass().getSuperclass().getDeclaredField("alma");
			System.out.println("is there alma? "+f2);
			System.out.println("f2: "+f2.getInt(ex1));
			f2.setInt(ex1, 2);
			System.out.println("result: "+ f2.getInt(ex1));
			Method toStringMethod = ex1Class.getMethod("toString");
			System.out.println("the method call results to: "+toStringMethod.invoke(ex1));
			Method sumMethod = ex1Class.getMethod("sum", int.class, int.class);
			System.out.println("sum call: "+sumMethod.invoke(ex1, 5, 7));
			for(var cc: ex1Class.getDeclaredConstructors()) {
				System.out.println("cc: "+cc);
			}
			Constructor<?> constructor = ex1Class.getDeclaredConstructor();
			System.out.println(constructor.newInstance());
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static class Example1 {
		public static String NAME = "Example 1 name";
		private int alma;
		private int korte;
		String dio;
		protected Long banan;
		public boolean kiwi = false;
		public Boolean k2 = Boolean.TRUE;
		
		public String toString() {
			return "alma: "+alma;
		}
		
		public int sum(int a, int b) {
			return a+b;
		}
	}

	private static class Example2 extends Example1 {
		protected int c;
		public int d;
	}
	
	private record MyRecord(int x, int y) {
		
	}

}
