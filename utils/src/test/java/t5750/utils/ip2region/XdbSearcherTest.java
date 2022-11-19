package t5750.utils.ip2region;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.lionsoul.ip2region.xdb.Searcher;

/**
 * 缓存整个 xdb 数据
 */
public class XdbSearcherTest {
	public static void main(String[] args) throws IOException {
		// 1、从 dbPath 加载整个 xdb 到内存。
		byte[] cBuff;
		try {
			cBuff = Searcher.loadContentFromFile(Ip2regionUtil.DB_PATH);
		} catch (Exception e) {
			System.out.printf("failed to load content from `%s`: %s\n",
					Ip2regionUtil.DB_PATH, e);
			return;
		}
		// 2、使用上述的 cBuff 创建一个完全基于内存的查询对象。
		Searcher searcher;
		try {
			searcher = Searcher.newWithBuffer(cBuff);
		} catch (Exception e) {
			System.out.printf("failed to create content cached searcher: %s\n",
					e);
			return;
		}
		// 3、查询
		String ip = "1.2.3.4";
		try {
			long sTime = System.nanoTime();
			String region = searcher.search(ip);
			long cost = TimeUnit.NANOSECONDS
					.toMicros((long) (System.nanoTime() - sTime));
			System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n",
					region, searcher.getIOCount(), cost);
		} catch (Exception e) {
			System.out.printf("failed to search(%s): %s\n", ip, e);
		}
		// 4、关闭资源 - 该 searcher 对象可以安全用于并发，等整个服务关闭的时候再关闭 searcher
		searcher.close();
		// 备注：并发使用，用整个 xdb 数据缓存创建的查询对象可以安全的用于并发，也就是你可以把这个 searcher
		// 对象做成全局对象去跨线程访问。
	}
}