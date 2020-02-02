package t5750.javassist.util;

public final class JavassistUtil {
	public static final String T5750 = "t5750";
	public static final String JAVASSIST = T5750 + ".javassist.";
	public static final String DOMAIN = JAVASSIST + "domain.";
	public static final String SERVICE = JAVASSIST + "service.";
	public static final String POINT = "java.awt.Point";
	public static final String[] ARGS = new String[] { "World", "T5750" };
	public static final String PATH_DOMAIN = DOMAIN.replace(".", "/");
	public static final String PATH_SERVICE = SERVICE.replace(".", "/");
}