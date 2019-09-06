package evangel.jvm;

import java.util.UUID;

/**
 * 探索StringTable提升YGC性能<br/>
 * https://www.jianshu.com/p/5524fce8b08f
 */
public class StringInternTest {
	// java -verbose:gc -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintStringTableStatistics -Xmx1g -Xms1g -Xmn64m evangel.jvm.StringInternTest
	// java -verbose:gc -XX:+PrintGC -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:CMSInitiatingOccupancyFraction=75 -XX:+UseCMSInitiatingOccupancyOnly -XX:+PrintStringTableStatistics -Xmx1g -Xms1g -Xmn64m -XX:StringTableSize=2500000 evangel.jvm.StringInternTest
	public static void main(String[] args) throws Exception {
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			UUID.randomUUID().toString().intern();
			if (i >= 100000 && i % 100000 == 0) {
				System.out.println("i=" + i);
			}
		}
	}
}