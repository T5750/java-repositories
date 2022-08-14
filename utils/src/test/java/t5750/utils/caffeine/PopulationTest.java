package t5750.utils.caffeine;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import com.github.benmanes.caffeine.cache.*;
import com.github.benmanes.caffeine.cache.stats.CacheStats;

/**
 * https://github.com/ben-manes/caffeine/wiki/Population-zh-CN
 */
public class PopulationTest {
	private static final String KEY = "name";
	private static final String VALUE = "T5750";

	/**
	 * 手动加载
	 */
	@Test
	public void manual() {
		Cache<String, Object> cache = Caffeine.newBuilder()
				.expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10_000)
				.build();
		// 查找一个缓存元素， 没有查找到的时候返回null
		Object name = cache.getIfPresent(KEY);
		System.out.println(name);
		// 查找缓存，如果缓存不存在则生成缓存元素, 如果无法生成则返回null
		name = cache.get(KEY, k -> {
			System.out.println(k);
			return VALUE;
		});
		System.out.println(name);
		// 添加或者更新一个缓存元素
		cache.put(KEY, VALUE);
		// 移除一个缓存元素
		cache.invalidate(KEY);
		System.out.println(cache.getIfPresent(KEY));
	}

	/**
	 * 自动加载
	 */
	@Test
	public void loading() {
		LoadingCache<String, Object> cache = Caffeine.newBuilder()
				.maximumSize(10_000).expireAfterWrite(10, TimeUnit.MINUTES)
				.build(key -> {
					System.out.println(key);
					return VALUE;
				});
		// 查找缓存，如果缓存不存在则生成缓存元素, 如果无法生成则返回null
		Object name = cache.get(KEY);
		System.out.println(name);
	}

	/**
	 * 手动异步加载
	 */
	@Test
	public void asynchronousManual()
			throws ExecutionException, InterruptedException {
		AsyncCache<String, Object> cache = Caffeine.newBuilder()
				.expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(10_000)
				.buildAsync();
		// 查找一个缓存元素， 没有查找到的时候返回null
		CompletableFuture<Object> name = cache.getIfPresent(KEY);
		System.out.println(name);
		// 查找缓存元素，如果不存在，则异步生成
		name = cache.get(KEY, k -> {
			System.out.println(k);
			return VALUE;
		});
		System.out.println(name.get());
		// 添加或者更新一个缓存元素
		cache.put(KEY, name);
		// 移除一个缓存元素
		cache.synchronous().invalidate(KEY);
	}

	/**
	 * 自动异步加载
	 */
	@Test
	public void asynchronouslyLoading()
			throws ExecutionException, InterruptedException {
		AsyncLoadingCache<String, Object> cache = Caffeine.newBuilder()
				.maximumSize(10_000).expireAfterWrite(10, TimeUnit.MINUTES)
				// 你可以选择: 去异步的封装一段同步操作来生成缓存元素
				.buildAsync(key -> {
					System.out.println(key);
					return VALUE;
				});
		// 你也可以选择: 构建一个异步缓存元素操作并返回一个future
		// .buildAsync((key, executor) -> createExpensiveGraphAsync(key,
		// executor));
		// 查找缓存元素，如果其不存在，将会异步进行生成
		CompletableFuture<Object> name = cache.get(KEY);
		System.out.println(name.get());
	}

	/**
	 * 移除监听器
	 */
	@Test
	public void removal() {
		Cache<String, Object> cache = Caffeine.newBuilder().evictionListener(
				(String key, Object graph, RemovalCause cause) -> System.out
						.printf("Key %s was evicted (%s)%n", key, cause))
				.removalListener((String key, Object graph,
						RemovalCause cause) -> System.out.printf(
								"Key %s was removed (%s)%n", key, cause))
				.build();
		cache.put(KEY, VALUE);
		// 失效所有的key
		cache.invalidateAll();
	}

	@Test
	public void statistics() {
		Cache<String, Object> cache = Caffeine.newBuilder().maximumSize(10_000)
				.recordStats().build();
		cache.put(KEY, VALUE);
		System.out.println(cache.getIfPresent(KEY));
		CacheStats cacheStats = cache.stats();
		System.out.println(
				cacheStats.hitRate() + ", " + cacheStats.evictionCount() + ", "
						+ cacheStats.averageLoadPenalty());
	}
}
