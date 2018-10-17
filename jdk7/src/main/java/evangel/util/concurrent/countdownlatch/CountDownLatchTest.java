package evangel.util.concurrent.countdownlatch;

/**
 * 检测闭锁的功能
 */
public class CountDownLatchTest {
	public static void main(String[] args) {
		boolean result = false;
		try {
			result = ApplicationStartupUtil.checkExternalServices();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out
				.println("External services validation completed !! Result was :: "
						+ result);
	}
}
