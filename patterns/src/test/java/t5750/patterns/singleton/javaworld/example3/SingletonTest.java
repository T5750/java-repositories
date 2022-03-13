package t5750.patterns.singleton.javaworld.example3;

import java.io.*;

import junit.framework.Assert;
import junit.framework.TestCase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SingletonTest extends TestCase {
	private Singleton sone = null, stwo = null;
	private static final Logger LOGGER = LogManager
			.getLogger(SingletonTest.class);

	public SingletonTest(String name) {
		super(name);
	}

	public void setUp() {
		sone = Singleton.INSTANCE;
		stwo = Singleton.INSTANCE;
	}

	public void testSerialize() {
		LOGGER.info("testing singleton serialization...");
		writeSingleton();
		Singleton s1 = readSingleton();
		Singleton s2 = readSingleton();
		Assert.assertEquals(true, s1 == s2);
	}

	private void writeSingleton() {
		try {
			FileOutputStream fos = new FileOutputStream("serializedSingleton");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			Singleton s = Singleton.INSTANCE;
			oos.writeObject(Singleton.INSTANCE);
			oos.flush();
		} catch (NotSerializableException se) {
			LOGGER.fatal("Not Serializable Exception: " + se.getMessage());
		} catch (IOException iox) {
			LOGGER.fatal("IO Exception: " + iox.getMessage());
		}
	}

	private Singleton readSingleton() {
		Singleton s = null;
		try {
			FileInputStream fis = new FileInputStream("serializedSingleton");
			ObjectInputStream ois = new ObjectInputStream(fis);
			s = (Singleton) ois.readObject();
		} catch (ClassNotFoundException cnf) {
			LOGGER.fatal("Class Not Found Exception: " + cnf.getMessage());
		} catch (NotSerializableException se) {
			LOGGER.fatal("Not Serializable Exception: " + se.getMessage());
		} catch (IOException iox) {
			LOGGER.fatal("IO Exception: " + iox.getMessage());
		}
		return s;
	}

	public void testUnique() {
		LOGGER.info("testing singleton uniqueness...");
		Singleton another = new Singleton();
		LOGGER.info("checking singletons for equality");
		Assert.assertEquals(true, sone == stwo);
	}
}