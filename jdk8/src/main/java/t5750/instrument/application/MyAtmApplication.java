package t5750.instrument.application;

public class MyAtmApplication {
	public static void run(String[] args) throws Exception {
		System.out.println("[Application] Starting ATM application");
		MyAtm.withdrawMoney(Integer.parseInt(args[2]));
		Thread.sleep(Long.valueOf(args[1]));
		MyAtm.withdrawMoney(Integer.parseInt(args[3]));
		Thread.sleep(30000);
	}
}