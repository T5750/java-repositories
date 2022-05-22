package t5750.future;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class CompletableFutureThenComposeTest {
	public CompletableFuture<Integer> findAccountNumber() {
		int findAccountNumber = 10;
		System.out.println(Thread.currentThread() + " findAccountNumber: "
				+ findAccountNumber);
		sleep(1);
		return CompletableFuture.completedFuture(findAccountNumber);
	}

	public CompletableFuture<Integer> calculateBalance(int accountNumber) {
		int calculateBalance = accountNumber * accountNumber;
		System.out.println(Thread.currentThread() + " calculateBalance: "
				+ calculateBalance);
		sleep(1);
		return CompletableFuture.completedFuture(calculateBalance);
	}

	public CompletableFuture<Integer> notifyBalance(Integer balance) {
		System.out.println(
				Thread.currentThread() + " Sending Notification: " + balance);
		sleep(1);
		return CompletableFuture.completedFuture(balance);
	}

	private void sleep(int seconds) {
		try {
			TimeUnit.SECONDS.sleep(seconds);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * CompletableFuture thenCompose
	 */
	@Test
	public void completableFutureThenCompose() {
		Integer join = findAccountNumber()
				.thenComposeAsync(this::calculateBalance)
				.thenCompose(this::notifyBalance).join();
		assertEquals(new Integer(100), join);
	}

	public CompletableFuture<String> findFirstName() {
		return CompletableFuture.supplyAsync(() -> {
			sleep(1);
			return "T5750";
		});
	}

	public CompletableFuture<String> findLastName() {
		return CompletableFuture.supplyAsync(() -> {
			sleep(1);
			return "Evaneo";
		});
	}

	/**
	 * CompletableFuture thenCombine
	 */
	@Test
	public void completableFutureThenCombine() {
		CompletableFuture<String> thenCombine = findFirstName()
				.thenCombine(findLastName(), (firstName, lastname) -> {
					return firstName + lastname;
				});
		String fullName = thenCombine.join();
		assertEquals("T5750Evaneo", fullName);
	}

	/**
	 * CompletableFuture allOf
	 */
	@Test
	public void completableFutureAllOf() {
		List<CompletableFuture<String>> list = new ArrayList<>();
		IntStream.range(0, 5).forEach(num -> {
			list.add(findFirstName());
		});
		// Created All of object
		CompletableFuture<Void> allfuture = CompletableFuture
				.allOf(list.toArray(new CompletableFuture[list.size()]));
		CompletableFuture<List<String>> allFutureList = allfuture
				.thenApply(val -> {
					return list.stream().map(f -> f.join())
							.collect(Collectors.toList());
				});
		CompletableFuture<String> futureHavingAllValues = allFutureList
				.thenApply(fn -> {
					System.out.println("I am here");
					return fn.stream().collect(Collectors.joining());
				});
		String concatenateString = futureHavingAllValues.join();
		assertEquals("T5750T5750T5750T5750T5750", concatenateString);
	}
}