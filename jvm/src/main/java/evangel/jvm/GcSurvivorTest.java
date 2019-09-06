package evangel.jvm;

/**
 * JVM Survivor行为一探究竟<br/>
 * https://www.jianshu.com/p/f91fde4628a5
 */
public class GcSurvivorTest {
	// -XX:+PrintTenuringDistribution
	// -verbose:gc -Xmx200M -Xmn50M -XX:TargetSurvivorRatio=60 -XX:+PrintTenuringDistribution -XX:+PrintGCDetails -XX:+PrintGCDateStamps -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:MaxTenuringThreshold=3
	public static void main(String[] args) throws InterruptedException {
		// 这两个对象不会被回收, 用于在s0和s1不断来回拷贝增加age直到达到PretenureSizeThreshold晋升到old
		byte[] byte1m_1 = new byte[1 * 512 * 1024];
		byte[] byte1m_2 = new byte[1 * 512 * 1024];
		// YoungGC后, byte1m_1 和 byte1m_2的age为1
		youngGc(1);
		Thread.sleep(3000);
		// 再次YoungGC后, byte1m_1 和 byte1m_2的age为2
		youngGc(1);
		Thread.sleep(3000);
		// 第三次YoungGC后, byte1m_1 和 byte1m_2的age为3
		youngGc(1);
		Thread.sleep(3000);
		// 这次再ygc时, 由于byte1m_1和byte1m_2的年龄已经是3，且MaxTenuringThreshold=3,
		// 所以这两个对象会晋升到Old区域,且ygc后, s0(from)和s1(to)空间中的对象被清空
		youngGc(1);
		Thread.sleep(3000);
		// main方法中分配的对象不会被回收, 不断分配是为了达到TargetSurvivorRatio这个比例指定的值,
		// 即5M*60%=3M(Desired survivor size)，说明:
		// 5M为S区的大小，60%为TargetSurvivorRatio参数指定，如下三个对象分配后就能够达到Desired survivor
		// size
		byte[] byte1m_4 = new byte[1 * 1024 * 1024];
		byte[] byte1m_5 = new byte[1 * 1024 * 1024];
		byte[] byte1m_6 = new byte[1 * 1024 * 1024];
		// 这次ygc时, 由于s区已经占用达到了60%(-XX:TargetSurvivorRatio=60),
		// 所以会重新计算对象晋升的age，计算公式为：min(age, MaxTenuringThreshold) = 1
		youngGc(1);
		Thread.sleep(3000);
		// 由于前一次ygc时算出age=1, 所以这一次再ygc时, byte1m_4, byte1m_5, byte1m_6就会晋升到Old区,
		// 而不需要等MaxTenuringThreshold这么多次, 此次ygc后, s0(from)和s1(to)空间中对象再次被清空,
		// 对象全部晋升到old
		youngGc(1);
		Thread.sleep(3000);
		System.out.println("hello world");
	}

	private static void youngGc(int ygcTimes) {
		for (int i = 0; i < ygcTimes * 40; i++) {
			byte[] byte1m = new byte[1 * 1024 * 1024];
		}
	}
}
