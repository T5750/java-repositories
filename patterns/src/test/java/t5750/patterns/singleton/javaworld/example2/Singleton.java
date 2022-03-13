package t5750.patterns.singleton.javaworld.example2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Singleton {
	private static Singleton singleton = null;
	private static final Logger LOGGER = LogManager
			.getLogger(Singleton.class);
	private static boolean firstThread = true;

	protected Singleton() {
		// Exists only to defeat instantiation.
	}

	public synchronized static Singleton getInstance() {
		if (singleton == null) {
			simulateRandomActivity();
			singleton = new Singleton();
		}
		LOGGER.info("created singleton: " + singleton);
		return singleton;
	}

	private static void simulateRandomActivity() {
		try {
			if (firstThread) {
				firstThread = false;
				LOGGER.info("sleeping...");
				// This nap should give the second thread enough time
				// to get by the first thread.
				Thread.currentThread().sleep(50);
			}
		} catch (InterruptedException ex) {
			LOGGER.warn("Sleep interrupted");
		}
	}
}