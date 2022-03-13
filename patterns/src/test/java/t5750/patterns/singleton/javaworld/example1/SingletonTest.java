package t5750.patterns.singleton.javaworld.example1;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingletonTest extends TestCase {
	private ClassicSingleton sone = null, stwo = null;
	private static final Logger LOGGER = LogManager
			.getLogger(SingletonTest.class);

	public SingletonTest(String name) {
		super(name);
	}

	public void setUp() {
		LOGGER.info("getting singleton...");
		sone = ClassicSingleton.getInstance();
		LOGGER.info("...got singleton: " + sone);
		LOGGER.info("getting singleton...");
		stwo = ClassicSingleton.getInstance();
		LOGGER.info("...got singleton: " + stwo);
	}

	public void testUnique() {
		LOGGER.info("checking singletons for equality");
		Assert.assertEquals(true, sone == stwo);
	}
}