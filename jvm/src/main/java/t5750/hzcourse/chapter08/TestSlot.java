package t5750.hzcourse.chapter08;

/**
 * -verbose:gc
 */
public class TestSlot {
	public static void main(String[] args) {
		byte[] placeholder = new byte[64 * 1024 * 1024];
		System.gc();
	}
}
