package evangel.keyword.synchronizedtest;

/**
 * 使用 javap -c SynchronizedTest 可以查看编译之后的具体信息
 */
public class SynchronizedTest {
	public static void main(String[] args) {
		synchronized (SynchronizedTest.class) {
			System.out.println("SynchronizedTest");
		}
	}
}
