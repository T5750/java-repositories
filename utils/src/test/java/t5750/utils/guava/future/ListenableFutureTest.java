package t5750.utils.guava.future;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.annotation.Nullable;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.util.concurrent.*;

/**
 * https://mp.weixin.qq.com/s?__biz=MzA5MTkxMDQ4MQ%3D%3D&chksm=88621b9bbf15928dd4c26f52b2abb0e130cde02100c432f33f0e90123b5e4b20d43017c1030e&idx=1&lang=zh_CN&mid=2648933285&scene=21&sn=f5507c251b84c3405f2fe0f7fb1da97d&token=1916804008#wechat_redirect
 */
public class ListenableFutureTest {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(ListenableFutureTest.class);

	/**
	 * 案例1：异步执行任务完毕之后回调
	 */
	@Test
	public void submitToListeningExecutor()
			throws ExecutionException, InterruptedException {
		ExecutorService delegate = Executors.newFixedThreadPool(5);
		try {
			ListeningExecutorService executorService = MoreExecutors
					.listeningDecorator(delegate);
			ListenableFuture<Integer> submit = executorService.submit(() -> {
				LOGGER.info("{}", System.currentTimeMillis());
				TimeUnit.SECONDS.sleep(2);
				// int i = 10 / 0;
				LOGGER.info("{}", System.currentTimeMillis());
				return 10;
			});
			Futures.addCallback(submit, new FutureCallback<Integer>() {
				@Override
				public void onSuccess(@Nullable Integer result) {
					LOGGER.info("执行成功:{}", result);
				}

				@Override
				public void onFailure(Throwable t) {
					try {
						TimeUnit.MILLISECONDS.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					LOGGER.error("执行任务发生异常:" + t.getMessage(), t);
				}
			}, MoreExecutors.directExecutor());
			LOGGER.info("{}", submit.get());
		} finally {
			delegate.shutdown();
		}
	}

	/**
	 * 示例2：获取一批异步任务的执行结果
	 */
	@Test
	public void getAsynchronousTasks()
			throws ExecutionException, InterruptedException {
		ExecutorService delegate = Executors.newFixedThreadPool(5);
		try {
			ListeningExecutorService executorService = MoreExecutors
					.listeningDecorator(delegate);
			List<ListenableFuture<Integer>> futureList = new ArrayList<>();
			for (int i = 5; i >= 0; i--) {
				int j = i;
				futureList.add(executorService.submit(() -> {
					TimeUnit.SECONDS.sleep(j);
					return j;
				}));
			}
			// 获取一批任务的执行结果
			List<Integer> resultList = Futures.allAsList(futureList).get();
			// 输出
			resultList.forEach(item -> {
				LOGGER.info("{}", item);
			});
		} finally {
			delegate.shutdown();
		}
	}

	/**
	 * 示例3：一批任务异步执行完毕之后回调
	 */
	@Test
	public void callbackAfterBatchTasks() {
		ExecutorService delegate = Executors.newFixedThreadPool(5);
		try {
			ListeningExecutorService executorService = MoreExecutors
					.listeningDecorator(delegate);
			List<ListenableFuture<Integer>> futureList = new ArrayList<>();
			for (int i = 5; i >= 0; i--) {
				int j = i;
				futureList.add(executorService.submit(() -> {
					TimeUnit.SECONDS.sleep(j);
					LOGGER.info("{}", j);
					return j;
				}));
			}
			ListenableFuture<List<Integer>> listListenableFuture = Futures
					.allAsList(futureList);
			Futures.addCallback(listListenableFuture,
					new FutureCallback<List<Integer>>() {
						@Override
						public void onSuccess(@Nullable List<Integer> result) {
							LOGGER.info("result中所有结果之和：" + result.stream()
									.reduce(Integer::sum).get());
						}

						@Override
						public void onFailure(Throwable t) {
							LOGGER.error("执行任务发生异常:" + t.getMessage(), t);
						}
					}, MoreExecutors.directExecutor());
			LOGGER.info("{}", listListenableFuture.get().stream()
					.reduce(Integer::sum).get());
		} catch (ExecutionException | InterruptedException e) {
			throw new RuntimeException(e);
		} finally {
			delegate.shutdown();
		}
	}
}