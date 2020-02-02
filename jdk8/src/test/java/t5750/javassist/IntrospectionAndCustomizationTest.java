package t5750.javassist;

import org.junit.Before;
import org.junit.Test;

import javassist.*;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import javassist.expr.NewArray;
import t5750.javassist.annotation.Author;
import t5750.javassist.util.JavassistUtil;

/**
 * 4. Introspection and customization
 */
public class IntrospectionAndCustomizationTest {
	private ClassPool pool;

	@Before
	public void setup() {
		pool = ClassPool.getDefault();
	}

	@Test
	public void addMethod() throws Exception {
		CtClass cc = pool.get(JavassistUtil.POINT);
		CtMethod m = CtNewMethod.make(
				"void move(int newX, int newY, int newZ) { move(newX, newY); }",
				cc);
		cc.addMethod(m);
		cc.writeFile(JavassistUtil.T5750);
	}

	/**
	 * 4.1 Inserting source text at the beginning/end of a method body<br/>
	 * $0, $1, $2, ...
	 */
	@Test
	public void actualParameters() throws Exception {
		CtClass cc = pool.get(JavassistUtil.POINT);
		CtMethod m = cc.getDeclaredMethod("move");
		m.insertBefore("{ System.out.println($1); System.out.println($2); }");
		cc.writeFile(JavassistUtil.T5750);
	}

	/**
	 * $cflow
	 */
	@Test
	public void cflow() throws Exception {
		CtClass cc = pool.get(JavassistUtil.DOMAIN + "CflowDomain");
		CtMethod m = cc.getDeclaredMethod("fact");
		m.useCflow("fact");
		m.insertBefore("if ($cflow(fact) == 0)"
				+ "    System.out.println(\"fact \" + $1);");
		cc.writeFile();
	}

	/**
	 * addCatch()
	 */
	@Test
	public void addCatch() throws Exception {
		CtClass cc = pool.get(JavassistUtil.DOMAIN + "CflowDomain");
		CtMethod m = cc.getDeclaredMethod("fact");
		CtClass etype = ClassPool.getDefault().get("java.io.IOException");
		m.addCatch("{ System.out.println($e); throw $e; }", etype);
		cc.writeFile();
	}

	/**
	 * 4.2 Altering a method body<br/>
	 * Substituting source text for an existing expression
	 */
	@Test
	public void replaceExpr() throws Exception {
		CtClass cc = pool.get(JavassistUtil.DOMAIN + "CflowDomain");
		CtMethod m = cc.getDeclaredMethod("fact");
		m.instrument(new ExprEditor() {
			public void edit(MethodCall mc) throws CannotCompileException {
				if (mc.getClassName()
						.equals(JavassistUtil.DOMAIN + "CflowDomain")
						&& mc.getMethodName().equals("fact")) {
					// byte var7 = 0;
					mc.replace(
							"try{ $1 = 0; $_ = $proceed($$); } catch (Throwable t) { throw t; }");
				}
			}
		});
		cc.writeFile();
	}

	/**
	 * javassist.expr.NewArray
	 */
	@Test
	public void newArray() throws Exception {
		CtClass cc = pool.get(JavassistUtil.SERVICE + "Hello");
		CtMethod m = cc.getDeclaredMethod("list");
		m.instrument(new ExprEditor() {
			public void edit(NewArray a) throws CannotCompileException {
				a.replace("{ $1 = 5;$2 = 6; $_ = $proceed($$); }");
				System.out.println(a.getFileName());
			}
		});
		cc.writeFile();
	}

	/**
	 * 4.3 Adding a new method or field<br/>
	 * Adding a method
	 */
	@Test
	public void addAMethod() throws Exception {
		CtClass cc = pool.get(JavassistUtil.POINT);
		CtMethod m = CtNewMethod.make("public void xmove(int dx) { x += dx; }",
				cc);
		cc.addMethod(m);
		CtMethod cm = CtNewMethod.make(
				"public void ymove(int dy) { $proceed(0, dy); }", cc, "this",
				"move");
		cc.addMethod(cm);
		CtMethod mAbstract = new CtMethod(CtClass.voidType, "move",
				new CtClass[] { CtClass.intType }, cc);
		cc.addMethod(mAbstract);
		mAbstract.setBody("{ x += $1; }");
		cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
		cc.writeFile(JavassistUtil.T5750);
	}

	/**
	 * Mutual recursive methods
	 */
	@Test
	public void mutualRecursiveMethods() throws Exception {
		CtClass cc = pool.get(JavassistUtil.SERVICE + "Hello");
		CtMethod m = CtNewMethod.make("public abstract int m(int i);", cc);
		CtMethod n = CtNewMethod.make("public abstract int n(int i);", cc);
		cc.addMethod(m);
		cc.addMethod(n);
		m.setBody("{ return ($1 <= 0) ? 1 : (n($1 - 1) * $1); }");
		n.setBody("{ return m($1); }");
		cc.setModifiers(cc.getModifiers() & ~Modifier.ABSTRACT);
		cc.writeFile();
	}

	/**
	 * Adding a field
	 */
	@Test
	public void addField() throws Exception {
		CtClass cc = pool.get(JavassistUtil.POINT);
		CtField f = new CtField(CtClass.intType, "z", cc);
		// cc.addField(f);
		cc.addField(f, "0"); // initial value is 0
		CtField cf = CtField.make("public int w = 0;", cc);
		cc.addField(cf);
		cc.writeFile(JavassistUtil.T5750);
	}

	/**
	 * 4.4 Annotations
	 */
	@Test
	public void annotations() throws Exception {
		CtClass cc = pool.get(JavassistUtil.SERVICE + "Hello");
		Object[] all = cc.getAnnotations();
		Author a = (Author) all[0];
		String name = a.name();
		int year = a.year();
		System.out.println("name: " + name + ", year: " + year);
	}

	/**
	 * 4.6 Import
	 */
	@Test
	public void doImport() throws Exception {
		pool.importPackage("java.awt");
		CtClass cc = pool.get(JavassistUtil.SERVICE + "Hello");
		CtField f = CtField.make("public Point p;", cc);
		cc.addField(f);
		cc.writeFile();
	}
}