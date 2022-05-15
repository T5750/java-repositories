package t5750.utils.guava.future;

import static org.junit.Assert.*;

import java.util.Set;
import java.util.concurrent.*;

import org.junit.Test;
import t5750.utils.guava.future.exception.ListenableFutureException;
import com.google.common.util.concurrent.*;

/**
 * https://www.baeldung.com/guava-futures-listenablefuture
 */
public class ListenableFutureSimpleUnitTest {
	@Test
	public void whenSubmitToListeningExecutor_thenSuccess()
			throws ExecutionException, InterruptedException {
		ExecutorService execService = Executors.newSingleThreadExecutor();
		ListeningExecutorService listeningExecService = MoreExecutors
				.listeningDecorator(execService);
		ListenableFuture<Integer> asyncTask = listeningExecService
				.submit(() -> {
					TimeUnit.MILLISECONDS.sleep(500); // long running task
					return 5;
				});
		assertEquals(Integer.valueOf(5), asyncTask.get());
	}

	@Test
	public void givenJavaExecutor_whenSubmitListeningTask_thenSuccess()
			throws ExecutionException, InterruptedException {
		Executor executor = Executors.newSingleThreadExecutor();
		ListenableFutureService service = new ListenableFutureService();
		FutureTask<String> configFuture = service
				.fetchConfigTask("future.value");
		executor.execute(configFuture);
		assertTrue(configFuture.get().contains("future.value"));
		ListenableFutureTask<String> configListenableFuture = service
				.fetchConfigListenableTask("listenable.value");
		executor.execute(configListenableFuture);
		assertTrue(configListenableFuture.get().contains("listenable.value"));
	}

	@Test
	public void givenNonFailingTask_whenCallbackListen_thenSuccess() {
		Executor listeningExecutor = MoreExecutors.directExecutor();
		ListenableFuture<Integer> succeedingTask = new ListenableFutureService()
				.succeedingTask();
		Futures.addCallback(succeedingTask, new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				assertNotNull(result);
				assertTrue(result >= 0);
			}

			@Override
			public void onFailure(Throwable t) {
				fail("Succeeding task cannot failed" + t);
			}
		}, listeningExecutor);
	}

	@Test
	public void givenFailingTask_whenCallbackListen_thenThrows() {
		Executor listeningExecutor = MoreExecutors.directExecutor();
		ListenableFuture<Integer> failingTask = new ListenableFutureService()
				.failingTask();
		Futures.addCallback(failingTask, new FutureCallback<Integer>() {
			@Override
			public void onSuccess(Integer result) {
				fail("Failing task cannot succeed");
			}

			@Override
			public void onFailure(Throwable t) {
				assertTrue(t instanceof ListenableFutureException);
			}
		}, listeningExecutor);
	}

	@Test
	public void givenNonFailingTask_whenDirectListen_thenListenerExecutes() {
		Executor listeningExecutor = MoreExecutors.directExecutor();
		int nextTask = 1;
		Set<Integer> runningTasks = ConcurrentHashMap.newKeySet();
		runningTasks.add(nextTask);
		ListenableFuture<Integer> nonFailingTask = new ListenableFutureService()
				.succeedingTask();
		nonFailingTask.addListener(() -> runningTasks.remove(nextTask),
				listeningExecutor);
		assertTrue(runningTasks.isEmpty());
	}

	@Test
	public void givenFailingTask_whenDirectListen_thenListenerExecutes() {
		final Executor listeningExecutor = MoreExecutors.directExecutor();
		int nextTask = 1;
		Set<Integer> runningTasks = ConcurrentHashMap.newKeySet();
		runningTasks.add(nextTask);
		final ListenableFuture<Integer> failingTask = new ListenableFutureService()
				.failingTask();
		failingTask.addListener(() -> runningTasks.remove(nextTask),
				listeningExecutor);
		assertTrue(runningTasks.isEmpty());
	}
}