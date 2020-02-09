package t5750.module.log.util;

import t5750.util.Global;

public class LogUtil {
	public static final String METHOD_ANNOTATION = Global.T5750
			+ ".module.log.annotation.ImportantLog";
	public static final String ANNOTATION_ARRAY = "fields";
	public static final String PATH_METHOD_ANNOTATION = METHOD_ANNOTATION
			.replace(".", Global.JAVASSIST_SEPARATOR);
	/**
	 * Lt5750/module/log/annotation/ImportantLog;
	 */
	public static final String ASM_METHOD_ANNOTATION = "L"
			+ PATH_METHOD_ANNOTATION + ";";
}