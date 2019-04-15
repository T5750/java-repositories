package evangel.hzcourse.chapter03;

/**
 * PretenureSizeThreshold参数只支持-XX:+UseSerialGC和-XX:+UseParNewGC
 */
public class TestPretenureSizeThresholdDemo {
	private static final int _1MB = 1024 * 1024;

	/**
	 * VM参数：-verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails
	 * -XX:SurvivorRatio=8 -XX:PretenureSizeThreshold=3145728
	 */
	public static void testPretenureSizeThreshold() {
		byte[] allocation;
		allocation = new byte[4 * _1MB]; // 直接分配在老年代中
	}

	public static void main(String[] args) {
		testPretenureSizeThreshold();
	}
}
