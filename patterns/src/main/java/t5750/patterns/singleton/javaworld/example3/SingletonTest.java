package t5750.patterns.singleton.javaworld.example3;

import java.io.*;

import org.apache.log4j.Logger;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SingletonTest extends TestCase {
	private Singleton sone = null, stwo = null;
	private static Logger logger = Logger.getRootLogger();

	public SingletonTest(String name) {
		super(name);
	}

	public void setUp() {
		sone = Singleton.INSTANCE;
		stwo = Singleton.INSTANCE;
	}

	public void testSerialize() {
		logger.info("testing singleton serialization...");
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
			logger.fatal("Not Serializable Exception: " + se.getMessage());
		} catch (IOException iox) {
			logger.fatal("IO Exception: " + iox.getMessage());
		}
	}

	private Singleton readSingleton() {
		Singleton s = null;
		try {
			FileInputStream fis = new FileInputStream("serializedSingleton");
			ObjectInputStream ois = new ObjectInputStream(fis);
			s = (Singleton) ois.readObject();
		} catch (ClassNotFoundException cnf) {
			logger.fatal("Class Not Found Exception: " + cnf.getMessage());
		} catch (NotSerializableException se) {
			logger.fatal("Not Serializable Exception: " + se.getMessage());
		} catch (IOException iox) {
			logger.fatal("IO Exception: " + iox.getMessage());
		}
		return s;
	}

	public void testUnique() {
		logger.info("testing singleton uniqueness...");
		Singleton another = new Singleton();
		logger.info("checking singletons for equality");
		Assert.assertEquals(true, sone == stwo);
	}
}