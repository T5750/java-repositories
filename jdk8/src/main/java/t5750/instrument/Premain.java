package t5750.instrument;

import java.lang.instrument.Instrumentation;

import t5750.instrument.agent.AtmTransformer;
import t5750.instrument.util.InstrumentUtil;

/**
 * 5. Using the Modified Class
 */
public class Premain {
	/**
	 * 5.2. Using Java Instrumentation
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		System.out.println("[Agent] In premain method");
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
		// inst.addTransformer(new ImportantLogClassTransformer());
		// inst.addTransformer(new AsmLogClassTransformer());
		transformClass(InstrumentUtil.MY_ATM, inst);
	}

	public static void agentmain(String agentArgs, Instrumentation inst) {
		System.out.println("[Agent] In agentmain method");
		transformClass(InstrumentUtil.MY_ATM, inst);
	}

	private static void transformClass(String className,
			Instrumentation instrumentation) {
		Class<?> targetCls = null;
		ClassLoader targetClassLoader = null;
		// see if we can get the class using forName
		try {
			targetCls = Class.forName(className);
			targetClassLoader = targetCls.getClassLoader();
			transform(targetCls, targetClassLoader, instrumentation);
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		// otherwise iterate all loaded classes and find what we want
		for (Class<?> clazz : instrumentation.getAllLoadedClasses()) {
			if (clazz.getName().equals(className)) {
				targetCls = clazz;
				targetClassLoader = targetCls.getClassLoader();
				transform(targetCls, targetClassLoader, instrumentation);
				return;
			}
		}
		throw new RuntimeException("Failed to find class [" + className + "]");
	}

	private static void transform(Class<?> clazz, ClassLoader classLoader,
			Instrumentation instrumentation) {
		AtmTransformer dt = new AtmTransformer(clazz.getName(), classLoader);
		instrumentation.addTransformer(dt, true);
		try {
			instrumentation.retransformClasses(clazz);
		} catch (Exception ex) {
			throw new RuntimeException(
					"Transform failed for class: [" + clazz.getName() + "]",
					ex);
		}
	}
}