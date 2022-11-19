package t5750.utils.ip2region;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.lionsoul.ip2region.xdb.Searcher;

/**
 * <a href=
 * "https://github.com/lionsoul2014/ip2region/tree/master/binding/java">ip2region
 * xdb java 查询客户端实现</a> 完全基于文件的查询
 */
public class FileSearcherTest {
	public static void main(String[] args) throws IOException {
		// 1、创建 searcher 对象
		Searcher searcher = null;
		try {
			searcher = Searcher.newWithFileOnly(Ip2regionUtil.DB_PATH);
		} catch (IOException e) {
			System.out.printf("failed to create searcher with `%s`: %s\n",
					Ip2regionUtil.DB_PATH, e);
			return;
		}
		// 2、查询
		try {
			long sTime = System.nanoTime();
			String region = searcher.search(Ip2regionUtil.IP);
			long cost = TimeUnit.NANOSECONDS
					.toMicros((long) (System.nanoTime() - sTime));
			System.out.printf("{region: %s, ioCount: %d, took: %d μs}\n",
					region, searcher.getIOCount(), cost);
		} catch (Exception e) {
			System.out.printf("failed to search(%s): %s\n", Ip2regionUtil.IP,
					e);
		}
		// 3、关闭资源
		searcher.close();
		// 备注：并发使用，每个线程需要创建一个独立的 searcher 对象单独使用。
	}
}