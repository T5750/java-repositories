package t5750.utils.ip2region;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.lionsoul.ip2region.xdb.Searcher;

/**
 * 缓存 VectorIndex 索引
 */
public class VectorIndexSearcherTest {
	public static void main(String[] args) throws IOException {
		// 1、从 dbPath 中预先加载 VectorIndex 缓存，并且把这个得到的数据作为全局变量，后续反复使用。
		byte[] vIndex;
		try {
			vIndex = Searcher.loadVectorIndexFromFile(Ip2regionUtil.DB_PATH);
		} catch (Exception e) {
			System.out.printf("failed to load vector index from `%s`: %s\n",
					Ip2regionUtil.DB_PATH, e);
			return;
		}
		// 2、使用全局的 vIndex 创建带 VectorIndex 缓存的查询对象。
		Searcher searcher;
		try {
			searcher = Searcher.newWithVectorIndex(Ip2regionUtil.DB_PATH,
					vIndex);
		} catch (Exception e) {
			System.out.printf(
					"failed to create vectorIndex cached searcher with `%s`: %s\n",
					Ip2regionUtil.DB_PATH, e);
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
		// 4、关闭资源
		searcher.close();
		// 备注：每个线程需要单独创建一个独立的 Searcher 对象，但是都共享全局的制度 vIndex 缓存。
	}
}