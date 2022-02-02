package t5750.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * FullGC实战：业务小姐姐查看图片时一直在转圈圈<br/>
 * https://www.jianshu.com/p/21946c12d521
 */
public class ParallelGcTest {
	private static final int _1M = 1024 * 1024;

	// -Xmx256m -Xms256m -Xmn128m -XX:+PrintGCDetails -XX:-UseAdaptiveSizePolicy
	public static void main(String[] args) {
		allocate();
	}

	/**
	 * 如果Young gc > 1.1 * Full GC，threshold就会一直减少<br/>
	 * 如果1.1 * Young GC < Full * GC，threshold就会一直增加
	 */
	private static void allocate() {
		// 强引用分配的对象, 为了触发FGC
		List<byte[]> holder = new ArrayList<>();
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			byte[] tmp = new byte[_1M];
			// 不要让FGC来的太快
			if (i % 10 == 0) {
				holder.add(tmp);
			}
		}
	}
}
