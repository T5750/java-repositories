package evangel.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicInteger例子
 */
public class AtomicIntegerTest {
	static AtomicInteger ai = new AtomicInteger(1);

	public static void main(String[] args) {
		System.out.println(ai.getAndIncrement());
		System.out.println(ai.get());
	}
}