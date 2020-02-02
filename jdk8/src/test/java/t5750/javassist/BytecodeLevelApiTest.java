package t5750.javassist;

import java.io.*;
import java.util.List;

import org.junit.Test;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.Modifier;
import javassist.bytecode.*;
import t5750.javassist.util.JavassistUtil;

/**
 * 5. Bytecode level API - 10. Debug
 */
public class BytecodeLevelApiTest {
	/**
	 * 5.1 Obtaining a ClassFile object
	 */
	@Test
	public void obtainClassFile() throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(
				JavassistUtil.PATH_DOMAIN + "Rectangle.class"));
		ClassFile cf = new ClassFile(new DataInputStream(fin));
		System.out.println(cf.getName());
		// You can create a new class file from scratch.
		ClassFile cfFoo = new ClassFile(false, JavassistUtil.DOMAIN + "Foo",
				null);
		cfFoo.setInterfaces(new String[] { "java.lang.Cloneable" });
		FieldInfo f = new FieldInfo(cfFoo.getConstPool(), "width", "I");
		f.setAccessFlags(AccessFlag.PUBLIC);
		cfFoo.addField(f);
		cfFoo.write(new DataOutputStream(
				new FileOutputStream(JavassistUtil.PATH_DOMAIN + "Foo.class")));
	}

	/**
	 * 5.2 Adding and removing a member
	 */
	@Test
	public void removeConstructor() throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(
				JavassistUtil.PATH_DOMAIN + "Rectangle.class"));
		ClassFile cf = new ClassFile(new DataInputStream(fin));
		List<MethodInfo> methodinfoList = cf.getMethods();
		if (null != methodinfoList && methodinfoList.size() > 0) {
			methodinfoList.remove(0);
			cf.write(new DataOutputStream(new FileOutputStream(
					JavassistUtil.PATH_DOMAIN + "Rectangle.class")));
		}
	}

	private void printCodeIterator(CodeAttribute ca) throws Exception {
		CodeIterator ci = ca.iterator();
		while (ci.hasNext()) {
			int index = ci.next();
			int op = ci.byteAt(index);
			System.out.println(Mnemonic.OPCODE[op]);
		}
	}

	/**
	 * 5.3 Traversing a method body
	 */
	@Test
	public void traverseMethodBody() throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(
				JavassistUtil.PATH_SERVICE + "Hello.class"));
		ClassFile cf = new ClassFile(new DataInputStream(fin));
		// we assume move is not overloaded.
		MethodInfo minfo = cf.getMethod("say");
		CodeAttribute ca = minfo.getCodeAttribute();
		printCodeIterator(ca);
	}

	/**
	 * 5.4 Producing a bytecode sequence
	 */
	@Test
	public void produceBytecodeSequence() throws Exception {
		BufferedInputStream fin = new BufferedInputStream(new FileInputStream(
				JavassistUtil.PATH_SERVICE + "Hello.class"));
		ClassFile cf = new ClassFile(new DataInputStream(fin));
		ConstPool cp = cf.getConstPool(); // constant pool table
		Bytecode b = new Bytecode(cp, 1, 0);
		b.addIconst(3);
		b.addReturn(CtClass.intType);
		CodeAttribute ca = b.toCodeAttribute();
		printCodeIterator(ca);
	}

	/**
	 * Bytecode can be used to construct a method
	 */
	@Test
	public void produceBytecodeConstructor() throws Exception {
		BufferedInputStream fin = new BufferedInputStream(
				new FileInputStream(JavassistUtil.PATH_DOMAIN + "Foo.class"));
		ClassFile cf = new ClassFile(new DataInputStream(fin));
		Bytecode code = new Bytecode(cf.getConstPool());
		code.addAload(0);
		code.addInvokespecial("java/lang/Object", MethodInfo.nameInit, "()V");
		code.addReturn(null);
		code.setMaxLocals(1);
		MethodInfo minfo = new MethodInfo(cf.getConstPool(),
				MethodInfo.nameInit, "()V");
		minfo.setCodeAttribute(code.toCodeAttribute());
		cf.addMethod(minfo);
		cf.write(new DataOutputStream(
				new FileOutputStream(JavassistUtil.PATH_DOMAIN + "Foo.class")));
	}

	/**
	 * 7. Varargs
	 */
	@Test
	public void varargs() throws Exception {
		ClassPool pool = ClassPool.getDefault();
		CtClass cc = pool
				.get(JavassistUtil.DOMAIN + "Rectangle");/* target class */
		CtMethod m = CtMethod.make(
				"public int length(int[] args) { return args.length; }", cc);
		m.setModifiers(m.getModifiers() | Modifier.VARARGS);
		cc.addMethod(m);
		cc.writeFile();
	}

	/**
	 * 10. Debug
	 */
	@Test
	public void debug() throws Exception {
		CtClass.debugDump = "./dump";
		varargs();
	}
}