package rf.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Task3 {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
	public static void main(String[] args) throws Exception {
		callEarlierMethods(MyClass.class, new MyClass(), sdf.parse("2023-04-18"));
	}

	private static <T> void callEarlierMethods(Class<T> clazz, T obj, java.util.Date date) {
		for(Method m : clazz.getDeclaredMethods()) {
			rf.example.Date dateAnnotation = m.getAnnotation(rf.example.Date.class);
			if(dateAnnotation != null && dateAnnotation.created() != null && m.getParameterCount() == 0 && isEarlier(dateAnnotation.created(), date)) {
				try {
					m.invoke(obj);
				} catch (IllegalAccessException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private static boolean isEarlier(String created, java.util.Date date) {
		try {
			java.util.Date createdAt = sdf.parse(created);
			return createdAt.toInstant().isBefore(date.toInstant());
		} catch (ParseException e) {
			throw new IllegalStateException(e);
		}
	}
	
	private static class MyClass {
		@Date(created = "2023-05-06")
		public void myPrint() {
			System.out.println("My Print");
		}
		
		@Date(created = "2021-05-06")
		public void myPrint2() {
			System.out.println("My Print2");
		}
		
		@Date(created = "2022-05-06")
		public void myPrint3() {
			System.out.println("My Print3");
		}
	}
}
 