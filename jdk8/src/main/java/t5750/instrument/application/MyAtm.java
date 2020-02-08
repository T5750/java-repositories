package t5750.instrument.application;

public class MyAtm {
	public static void withdrawMoney(int amount) throws InterruptedException {
		Thread.sleep(2000L); // processing going on here
		System.out.println("[Application] Successful Withdrawal of [" + amount
				+ "] units!");
	}
}