package t5750.patterns.singleton.javaworld.example2;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingletonTest extends TestCase {
	private static final Logger LOGGER = LogManager
			.getLogger(SingletonTest.class);
	private static Singleton singleton = null;

	public SingletonTest(String name) {
		super(name);
	}

	public void setUp() {
		singleton = null;
	}

	public void testUnique() throws InterruptedException {
		// Both threads call Singleton.getInstance().
		Thread threadOne = new Thread(new SingletonTestRunnable()),
				threadTwo = new Thread(new SingletonTestRunnable());
		threadOne.start();
		threadTwo.start();
		threadOne.join();
		threadTwo.join();
	}

	private static class SingletonTestRunnable implements Runnable {
		public void run() {
			// Get a reference to the singleton.
			Singleton s = Singleton.getInstance();
			// Protect singleton member variable from
			// multithreaded access.
			synchronized (SingletonTest.class) {
				if (singleton == null) // If local reference is null...
					singleton = s; // ...set it to the singleton
			}
			// Local reference must be equal to the one and
			// only instance of Singleton; otherwise, we have two
			// Singleton instances.
			Assert.assertEquals(true, s == singleton);
		}
	}
}
