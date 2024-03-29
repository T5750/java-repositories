package t5750.hzcourse.chapter04;

import java.util.ArrayList;
import java.util.List;

/**
 * JConsole-内存-jstat<br/>
 * -Xms100M -Xmx100M -XX:+UseSerialGC
 */
public class TestOOMObject {
	/**
	 * 内存占位符对象，一个OOMObject大约占64K
	 */
	static class OOMObject {
		public byte[] placeholder = new byte[64 * 1024];
	}

	public static void fillHeap(int num) throws InterruptedException {
		List<OOMObject> list = new ArrayList<OOMObject>();
		for (int i = 0; i < num; i++) {
			// 稍作延时，令监视曲线的变化更加明显
			Thread.sleep(50);
			list.add(new OOMObject());
		}
		System.gc();
	}

	public static void main(String[] args) throws Exception {
		fillHeap(1000);
	}
}
