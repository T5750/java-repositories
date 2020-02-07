package t5750.instrument;

import java.awt.*;
import java.security.ProtectionDomain;

import org.junit.Test;

import t5750.javassist.loader.JavaClassLoader;

/**
 * 5. Using the Modified Class
 */
public class PremainTest {
	public static final String METHOD_NAME = "transform";
	public static final String CLASS_BIN_NAME = "t5750.instrument.ImportantLogClassTransformer";
	public static final String PATH_CLASS_BIN_NAME = CLASS_BIN_NAME.replace(".",
			"/");

	public static void main(String[] args) throws Exception {
		System.out.println("PremainTest");
		System.out.println(new Point().toString());
	}

	/**
	 * Call ImportantLogClassTransformer+transform
	 */
	@Test
	public void testClassFileTransformer() throws Exception {
		JavaClassLoader loader = new JavaClassLoader();
		Class[] parameterTypes = new Class[] { ClassLoader.class, String.class,
				Class.class, ProtectionDomain.class, byte[].class };
		Object[] args = new Object[] { loader, PATH_CLASS_BIN_NAME, null, null,
				null };
		loader.invokeClassMethod(CLASS_BIN_NAME, METHOD_NAME, parameterTypes,
				args);
	}
}