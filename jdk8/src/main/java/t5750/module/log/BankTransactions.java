package t5750.module.log;

import t5750.module.log.annotation.ImportantLog;

public class BankTransactions {
	@ImportantLog(fields = { "1", "2" })
	public void login(String password, String accountId, String userName) {
		// login logic
	}

	@ImportantLog(fields = { "0", "1" })
	public void withdraw(String accountId, Double moneyToRemove) {
		// transaction logic
	}

	public static void main(String[] args) {
		BankTransactions bank = new BankTransactions();
		for (int i = 0; i < 100; i++) {
			String accountId = "account" + i;
			bank.login("password", accountId, "T5750");
			// bank.unimportantProcessing(accountId);
			bank.withdraw(accountId, Double.valueOf(i));
		}
		System.out.println("Transactions completed");
	}
}