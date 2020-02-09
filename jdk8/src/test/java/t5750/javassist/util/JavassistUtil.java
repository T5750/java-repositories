package t5750.javassist.util;

import t5750.util.Global;

public final class JavassistUtil {
	public static final String JAVASSIST = Global.T5750 + ".javassist.";
	public static final String DOMAIN = JAVASSIST + "domain.";
	public static final String SERVICE = JAVASSIST + "service.";
	public static final String[] ARGS = new String[] { "World", "T5750" };
	public static final String PATH_DOMAIN = DOMAIN.replace(".",
			Global.JAVASSIST_SEPARATOR);
	public static final String PATH_SERVICE = SERVICE.replace(".",
			Global.JAVASSIST_SEPARATOR);
}