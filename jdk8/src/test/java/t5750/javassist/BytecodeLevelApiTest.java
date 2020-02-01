package t5750.javassist;

import java.io.*;

import org.junit.Test;

import javassist.bytecode.AccessFlag;
import javassist.bytecode.ClassFile;
import javassist.bytecode.FieldInfo;
import t5750.javassist.util.JavassistUtil;

/**
 * Bytecode level API
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
}