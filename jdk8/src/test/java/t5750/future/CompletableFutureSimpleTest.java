package t5750.future;

import static org.junit.Assert.*;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * https://nirajsonawane.github.io/2019/01/27/Write-Clean-asynchronous-code-with-CompletableFuture-Java-8/
 */
public class CompletableFutureSimpleTest {
	public AtomicInteger someStateVaribale = new AtomicInteger(1);

	/**
	 * Create Simple Completable Future
	 */
	@Test
	public void simpleComletedCompletableFuture() {
		CompletableFuture<String> completableFuture = CompletableFuture
				.completedFuture("Some Value");
		assertTrue(completableFuture.isDone());
		try {
			assertEquals("Some Value", completableFuture.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	@Test
	public void simpleCompletableFuture() {
		CompletableFuture<String> completableFuture = new CompletableFuture<>();
		completableFuture.complete("Some Value");
		assertTrue(completableFuture.isDone());
		try {
			assertEquals("Some Value", completableFuture.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	public void process() {
		System.out.println(Thread.currentThread() + " Process");
		someStateVaribale.set(100);
	}

	/**
	 * Simple Asynchronous computation using runAsync
	 */
	@Test
	public void completableFutureRunAsync() {
		CompletableFuture<Void> runAsync = CompletableFuture
				.runAsync(() -> process());
		runAsync.join();
		assertEquals(100, someStateVaribale.get());
	}

	public String processSomeData() {
		System.out.println(Thread.currentThread() + " Processing Some Data");
		return "Some Value";
	}

	/**
	 * Simple Asynchronous computation using SupplyAsync
	 */
	@Test
	public void completableFutureSupplyAsync() {
		CompletableFuture<String> supplyAsync = CompletableFuture
				.supplyAsync(this::processSomeData);
		try {
			assertEquals("Some Value", supplyAsync.get()); // Blocking
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}

	/**
	 * CompletableFuture with Custom Executor
	 */
	@Test
	public void completableFutureSupplyAsyncWithExecutor() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
		CompletableFuture<String> supplyAsync = CompletableFuture
				.supplyAsync(this::processSomeData, newFixedThreadPool);
		try {
			assertEquals("Some Value", supplyAsync.get());
		} catch (ExecutionException | InterruptedException e) {
			fail("No Exception expected");
		}
	}
}
