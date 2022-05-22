package t5750.future;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

/**
 * CompletableFuture Callbacks and Chaining
 */
public class CompletableFutureThenApplyTest {
	public AtomicInteger someStateVaribale = new AtomicInteger(1);

	public String process() {
		System.out.println(Thread.currentThread() + " Process Method");
		sleep(1);
		return "Some Value";
	}

	private void sleep(Integer i) {
		try {
			TimeUnit.SECONDS.sleep(i);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void notifyThenAccept(String str) {
		System.out.println(Thread.currentThread() + " Received value: " + str);
		someStateVaribale.set(100);
		sleep(1);
	}

	public Integer notifyThenApply(String str) {
		System.out.println(Thread.currentThread() + " Received value: " + str);
		sleep(1);
		return 1;
	}

	public void notifyThenRun() {
		System.out.println(Thread.currentThread());
		sleep(1);
		someStateVaribale.set(100);
	}

	@Test
	public void completableFutureThenAccept() {
		// Non Blocking,notify method will be called automatically after
		// compilation or process method
		CompletableFuture.supplyAsync(this::process)
				.thenAccept(this::notifyThenAccept).join();
		assertEquals(100, someStateVaribale.get());
	}

	@Test
	public void completableFutureThenApply() {
		// Non Blocking will return some value
		Integer notificationId = CompletableFuture.supplyAsync(this::process)
				.thenApply(this::notifyThenApply).join();
		assertEquals(new Integer(1), notificationId);
	}

	@Test
	public void completableFutureThenRun() {
		CompletableFuture.supplyAsync(this::process)
				.thenRun(this::notifyThenRun).join();
		assertEquals(100, someStateVaribale.get());
	}
}