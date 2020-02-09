package t5750.javassist;

import org.junit.Before;
import org.junit.Test;

import javassist.*;
import t5750.javassist.loader.JavaClassLoader;
import t5750.javassist.loader.SampleLoader;
import t5750.javassist.service.Hello;
import t5750.javassist.service.impl.MyTranslator;
import t5750.javassist.util.JavassistUtil;
import t5750.util.Global;

/**
 * 3. Class loader
 */
public class ClassLoaderTest {
	private ClassPool pool;

	@Before
	public void setup() {
		pool = ClassPool.getDefault();
	}

	/**
	 * 3.1 The toClass method in CtClass
	 */
	@Test
	public void toClassMethod() throws Exception {
		CtClass cc = pool.get(JavassistUtil.SERVICE + "Hello");
		CtMethod m = cc.getDeclaredMethod("say");
		m.insertBefore("{ System.out.println(\"Hello.say():\"); }");
		Class c = cc.toClass();
		Hello h = (Hello) c.newInstance();
		h.say();
	}

	/**
	 * 3.2 Class loading in Java
	 */
	@Test
	public void javaClassLoader() throws Exception {
		ClassLoader loader = this.getClass().getClassLoader();
		Class clazz = loader.loadClass(JavassistUtil.SERVICE + "Hello");
		Object obj = clazz.newInstance();
		Hello b = (Hello) obj; // this always throws ClassCastException.
	}

	/**
	 * 3.3 Using javassist.Loader
	 */
	@Test
	public void javassistLoader() throws Throwable {
		Loader cl = new Loader(pool);
		CtClass ct = pool.get(JavassistUtil.DOMAIN + "Rectangle");
		ct.setSuperclass(pool.get(Global.POINT));
		Class c = cl.loadClass(JavassistUtil.DOMAIN + "Rectangle");
		Object rect = c.newInstance();
		System.out.println(rect.toString());
		// To run an application class MyApp with a MyTranslator object
		Translator t = new MyTranslator();
		cl.addTranslator(pool, t);
		cl.run(JavassistUtil.SERVICE + "MyApp", JavassistUtil.ARGS);
	}

	/**
	 * 3.4 Writing a class loader
	 */
	@Test
	public void testSampleLoader() throws Throwable {
		SampleLoader.main(JavassistUtil.ARGS);
	}

	/**
	 * 3.5 Modifying a system class
	 */
	@Test
	public void modifySystemClass() throws Exception {
		CtClass cc = pool.get("java.lang.String");
		CtField f = new CtField(CtClass.intType, "hiddenValue", cc);
		f.setModifiers(Modifier.PUBLIC);
		cc.addField(f);
		cc.writeFile(".");
		CtClass ccMyApp = pool.get(JavassistUtil.SERVICE + "MyApp");
		CtMethod m = ccMyApp.getDeclaredMethod("main");
		m.insertBefore("{ getHiddenValue(); }");
		ccMyApp.writeFile();
		// cd java-repositories/jdk8
		// java -Xbootclasspath/p:. t5750.javassist.service.MyApp
	}

	/**
	 * Dynamic Class Loading Example
	 */
	@Test
	public void dynamicJavaClassLoader() throws Exception {
		JavaClassLoader loader = new JavaClassLoader();
		loader.invokeClassMethod(JavassistUtil.SERVICE + "Hello", "say");
	}
}