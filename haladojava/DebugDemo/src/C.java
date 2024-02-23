
public class C extends B {

	Object data;

	public C(int i) {
		super(i);
	}

	@Override
	public void set(int i, Object v) {
		if (i == 0) {
			data = v;
		} else {
			super.set(i, v);
		}
	}

	@Override
	public void show() {
		System.out.println(data);
		if (next != null) {
			next.show();
		}
	}

}
