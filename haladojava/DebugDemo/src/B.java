
public class B {

	B next;

	public B(int i) {
		if (i > 0) {
			next = new C(i - 1);
		}
	}

	public void set(int i, Object v) {
		if (i <= 0) {
			throw new RuntimeException("Nonono");
		} else if (next != null) {
			next.set(i - 1, v);
		}
	}

	public void show() {
		System.out.println("H");
		next.show();
	}

}
