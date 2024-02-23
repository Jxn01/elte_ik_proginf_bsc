
public class A {

	public static void main(String[] args) {
		B b = new B(3);
		b.set(2, "Hello");
		b.set(1, 12);
		b.show();
		b.set(0, 'X');
	}

}
