package evangel.jvm;

import java.util.UUID;

/**
 * G1之REGION SIZE<br/>
 * https://www.jianshu.com/p/abafbb965fff
 */
public class G1StringTest {
	// -XX:+UseG1GC -verbose:gc -Xmx6144m -Xms2048m -XX:+PrintHeapAtGC
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			// 利用UUID不断生成字符串，这些字符串都会在堆中分配，导致不断塞满Eden区引起YoungGC
			UUID.randomUUID().toString();
			if (i >= 100000 && i % 100000 == 0) {
				System.out.println("i=" + i);
				Thread.sleep(3000);
			}
		}
		Thread.sleep(3000);
	}
}
