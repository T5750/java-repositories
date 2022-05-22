package t5750.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

/**
 * https://www.tutorialspoint.com/differences-between-completablefuture-and-future-in-java-9
 */
public class CompletableFutureTest {
	public static void main(String args[])
			throws ExecutionException, InterruptedException {
		Calculator calc = new Calculator(4, 7);
		CompletableFuture<Integer> future = CompletableFuture.supplyAsync(calc);
		future.thenAccept(result -> {
			System.out.println(result);
		});
		System.out.println("CompletableFutureTest End.... ");
		Thread.sleep(10000);
	}
}

// Calculator class
class Calculator implements Supplier<Integer> {
	private int x, y;

	public Calculator(int x, int y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public Integer get() {
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return x + y;
	}
}