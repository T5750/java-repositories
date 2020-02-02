package t5750.javassist;

import org.junit.Before;
import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import t5750.javassist.util.JavassistUtil;

/**
 * 2. ClassPool
 */
public class ClassPoolTest {
	private ClassPool pool;

	@Before
	public void setup() {
		pool = ClassPool.getDefault();
	}

	@Test
	public void main() throws Exception {
		CtClass cc = pool.get(JavassistUtil.DOMAIN + "Rectangle");
		cc.setSuperclass(pool.get(JavassistUtil.POINT));
		cc.writeFile();
		// Avoid out of memory
		cc.detach();
		// Cascaded ClassPools
		ClassPool child = new ClassPool(pool);
		child.insertClassPath("./classes");
		// Changing a class name for defining a new class
		cc = pool.get(JavassistUtil.POINT);
		CtClass cc1 = pool.get(JavassistUtil.POINT); // cc1 is identical to cc.
		cc.setName(JavassistUtil.DOMAIN + "Pair");
		// cc2 is identical to cc.
		CtClass cc2 = pool.get(JavassistUtil.DOMAIN + "Pair");
		// cc3 is not identical to cc.
		CtClass cc3 = pool.get(JavassistUtil.POINT);
		cc.writeFile();
		System.out.println("cc cc1: " + cc.equals(cc1));
		System.out.println("cc cc2: " + cc.equals(cc2));
		System.out.println("cc2 cc3: " + cc2.equals(cc3));
		// Renaming a frozen class for defining a new class
		// cc.setName("Pair"); // wrong since writeFile() has been called.
		CtClass ccPointPair = pool.getAndRename(JavassistUtil.POINT,
				JavassistUtil.DOMAIN + "PointPair");
		ccPointPair.writeFile();
	}
}