package t5750.util;

public class Global {
	public static final String T5750 = "t5750";
	/**
	 * Javassist separator
	 */
	public static final String JAVASSIST_SEPARATOR = "/";
	public static final String POINT = "java.awt.Point";
	public static final String PATH_POINT = POINT.replace(".",
			Global.JAVASSIST_SEPARATOR);
	public static final String INTEGER = "java.lang.Integer";
}