package t5750.javassist.loader;

import java.io.IOException;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.NotFoundException;
import t5750.javassist.util.JavassistUtil;

public class SampleLoader extends ClassLoader {
	/*
	 * Call MyApp.main().
	 */
	public static void main(String[] args) throws Throwable {
		SampleLoader s = new SampleLoader();
		Class c = s.loadClass(JavassistUtil.SERVICE + "MyApp");
		c.getDeclaredMethod("main", new Class[] { String[].class }).invoke(null,
				new Object[] { args });
	}

	private ClassPool pool;

	public SampleLoader() throws NotFoundException {
		pool = new ClassPool();
		pool.insertClassPath("./class"); // MyApp.class must be there.
	}

	/*
	 * Finds a specified class. The bytecode for that class can be modified.
	 */
	protected Class findClass(String name) throws ClassNotFoundException {
		try {
			CtClass cc = pool.get(name);
			// modify the CtClass object here
			byte[] b = cc.toBytecode();
			return defineClass(name, b, 0, b.length);
		} catch (NotFoundException e) {
			throw new ClassNotFoundException();
		} catch (IOException e) {
			throw new ClassNotFoundException();
		} catch (CannotCompileException e) {
			throw new ClassNotFoundException();
		}
	}
}