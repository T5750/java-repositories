package t5750.hzcourse.chapter06;

public class TestClass {
	private int m;

	public int inc() {
		return m + 1;
	}

	public static void main(String[] args) {
		System.out.println("Test class loading.");
		System.out.println(new TestClass().inc());
	}
}
