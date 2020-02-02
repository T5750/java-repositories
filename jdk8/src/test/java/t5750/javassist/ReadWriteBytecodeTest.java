package t5750.javassist;

import org.junit.Before;
import org.junit.Test;

import javassist.ClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.URLClassPath;
import t5750.javassist.util.JavassistUtil;

/**
 * 1. Reading and writing bytecode
 */
public class ReadWriteBytecodeTest {
	private ClassPool pool;
	private ClassPool poolNew;

	@Before
	public void setup() {
		pool = ClassPool.getDefault();
		poolNew = ClassPool.getDefault();
	}

	@Test
	public void main() throws Exception {
		CtClass cc = pool.get(JavassistUtil.DOMAIN + "Rectangle");
		cc.setSuperclass(pool.get(JavassistUtil.POINT));
		cc.writeFile();
		byte[] b = cc.toBytecode();
		Class clazz = cc.toClass();
		System.out.println(clazz.getSuperclass());
		// Defining a new class
		CtClass ccNew = poolNew.makeClass(JavassistUtil.DOMAIN + "Point");
		ccNew.writeFile();
		// Frozen classes
		ccNew.defrost();
		ccNew.setSuperclass(poolNew.get("java.awt.geom.Point2D"));
		ccNew.stopPruning(true);
		ccNew.writeFile();
	}

	@Test
	public void classSearchPath() throws Exception {
		// Class search path
		// http://localhost:8000/test2/IncOp.class
		ClassPath cp = new URLClassPath("localhost", 8000, "/", null);
		poolNew.insertClassPath(cp);
		CtClass ccPath = poolNew
				.makeClass(JavassistUtil.DOMAIN + "ClassSearchPath");
		ccPath.setSuperclass(poolNew.get("test2.IncOp"));
		ccPath.writeFile();
	}
}