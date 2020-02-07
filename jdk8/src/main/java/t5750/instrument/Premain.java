package t5750.instrument;

import java.lang.instrument.Instrumentation;

/**
 * 5. Using the Modified Class
 */
public class Premain {
	/**
	 * 5.2. Using Java Instrumentation
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("Premain");
		// inst.addTransformer(new ClassFileTransformer() {
		// @Override
		// public byte[] transform(ClassLoader l, String name, Class c,
		// ProtectionDomain d, byte[] b)
		// throws IllegalClassFormatException {
		// if (name.equals("java/lang/Integer")) {
		// CustomClassWriter cr = new CustomClassWriter(b);
		// return cr.addField();
		// }
		// if (name.equals(AsmUtil.PATH_POINT)) {
		// System.out.println("transform: " + name);
		// }
		// return b;
		// }
		// });
		inst.addTransformer(new ImportantLogClassTransformer());
	}
}