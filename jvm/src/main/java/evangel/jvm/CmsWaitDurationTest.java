package evangel.jvm;

/**
 * 一个有意思的CMS问题<br/>
 * https://www.jianshu.com/p/a322309b1d90
 * 1、老年代当前使用率是否达到阈值CMSInitiatingOccupancyFraction;
 * 2、判断当前新生代的对象是否能够全部顺利的晋升到老年代，如果不能，就提早触发一次老年代的收集，这是本案例中不停CMS的根本原因
 */
public class CmsWaitDurationTest {
	private static final int _1MB = 1024 * 1024;

	/**
	 * -Xmx20m -Xms20m -Xmn10m -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -XX:+PrintGCDetails -XX:+PrintHeapAtGC
	 * -XX:CMSWaitDuration=5000
	 */
	public static void main(String[] args) throws Exception {
		byte[] all1 = new byte[2 * _1MB];
		byte[] all2 = new byte[2 * _1MB];
		byte[] all3 = new byte[2 * _1MB];
		byte[] all4 = new byte[7 * _1MB];
		System.in.read();
	}
}
