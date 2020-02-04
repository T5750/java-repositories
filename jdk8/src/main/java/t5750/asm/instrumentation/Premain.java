package t5750.asm.instrumentation;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import t5750.asm.CustomClassWriter;

/**
 * 5. Using the Modified Class
 */
public class Premain {
	/**
	 * 5.2. Using Java Instrumentation
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Premain");
		inst.addTransformer(new ClassFileTransformer() {
			@Override
			public byte[] transform(ClassLoader l, String name, Class c,
					ProtectionDomain d, byte[] b)
					throws IllegalClassFormatException {
				if (name.equals("java/lang/Integer")) {
					CustomClassWriter cr = new CustomClassWriter(b);
					return cr.addField();
				}
				return b;
			}
		});
	}
}