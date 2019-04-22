package evangel.hzcourse.chapter10;

/**
 *
 */
public class TestIfdef {
	public static void main(String[] args) {
		if (true) {
			System.out.println("block 1");
		} else {
			System.out.println("block 2");
		}
		// 编译器将会提示“Unreachable code”
		// while (false) {
		// System.out.println("");
		// }
	}
}
