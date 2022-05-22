package t5750.future;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.*;

import org.junit.Test;

/**
 * https://nirajsonawane.github.io/2019/01/27/Write-Clean-asynchronous-code-with-CompletableFuture-Java-8/
 */
public class CompletableFutureExceptionTest {
	public int findAccountNumber() {
		int findAccountNumber = 10;
		System.out.println(Thread.currentThread() + " findAccountNumber: "
				+ findAccountNumber);
		sleep(1);
		return findAccountNumber;
	}

	public int calculateBalance(int accountNumber) {
		int calculateBalance = accountNumber * accountNumber;
		System.out.println(Thread.currentThread() + " calculateBalance: "
				+ calculateBalance);
		sleep(1);
		return calculateBalance;
	}

	public Integer notifyBalance(Integer balance) {
		System.out.println(
				Thread.currentThread() + " Sending Notification: " + balance);
		sleep(1);
		if (100 == balance) {
			CompletableFuture<String> future = new CompletableFuture<>();
			throw new RuntimeException("Invalid Balance Exception");
		}
		return balance;
	}

	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Handle Exceptions using exceptionally
	 */
	@Test
	public void completableFutureExceptionally() {
		CompletableFuture<Integer> thenApply = CompletableFuture
				.supplyAsync(this::findAccountNumber)
				.thenApply(this::calculateBalance)
				.thenApply(this::notifyBalance).exceptionally(ex -> {
					System.out.println("Got Some Exception " + ex.getMessage());
					System.out.println("Returning some default value");
					return 0;
				});
		Integer join = thenApply.join();
		assertEquals(new Integer(0), join);
	}

	/**
	 * Handle Exceptions using Handle Method
	 */
	@Test
	public void completableFutureHandle() {
		System.out.println("completableFutureHandle");
		CompletableFuture<Integer> thenApply = CompletableFuture
				.supplyAsync(this::findAccountNumber)
				.thenApply(this::calculateBalance)
				.thenApply(this::notifyBalance).handle((ok, ex) -> {
					System.out.println("Code That we want to run in finally ");
					if (ok != null) {
						System.out.println("No Exception !!");
					} else {
						System.out.println("Got Exception " + ex.getMessage());
						return -1;
					}
					return ok;
				});
		Integer join = thenApply.join();
		assertEquals(new Integer(-1), join);
	}

	/**
	 * Handle Exceptions using WhenComplete Method
	 */
	@Test
	public void completableFutureWhenComplete() {
		System.out.println("completableFutureHandle");
		CompletableFuture<Integer> thenApply = CompletableFuture
				.supplyAsync(this::findAccountNumber)
				.thenApply(this::calculateBalance)
				.thenApply(this::notifyBalance)
				.whenComplete((i, t) -> System.out
						.println(Thread.currentThread() + " finally action"))
				.exceptionally(ex -> {
					System.out.println("Got Some Exception " + ex.getMessage());
					System.out.println("Returning some default value");
					return 0;
				});
		Integer join = thenApply.join();
		assertEquals(new Integer(0), join);
	}

	public void notifyByEmail() {
		sleep(1);
		System.out.println(Thread.currentThread() + " Received value");
	}

	/**
	 * Chaining Callbacks
	 */
	@Test
	public void completableFutureThenApplyAccept() {
		CompletableFuture.supplyAsync(this::findAccountNumber)
				.thenApply(this::calculateBalance)
				.thenApply(t -> notifyBalance(20))
				.thenAccept((i) -> notifyByEmail()).join();
	}

	@Test
	public void completableFutureApplyAsync() {
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(2);
		ScheduledExecutorService newSingleThreadScheduledExecutor = Executors
				.newSingleThreadScheduledExecutor();
		CompletableFuture<Integer> completableFuture = CompletableFuture
				.supplyAsync(this::findAccountNumber, newFixedThreadPool)
				.thenApplyAsync(this::calculateBalance,
						newSingleThreadScheduledExecutor)
				.thenApplyAsync(t -> notifyBalance(20));
		Integer balance = completableFuture.join();
		assertEquals(Integer.valueOf(balance), Integer.valueOf(20));
	}
}