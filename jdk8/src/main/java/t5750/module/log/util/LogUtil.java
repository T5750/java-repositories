package t5750.module.log.util;

public class LogUtil {
	public static final String METHOD_ANNOTATION = "t5750.module.log.annotation.ImportantLog";
	public static final String ANNOTATION_ARRAY = "fields";
	public static final String PATH_METHOD_ANNOTATION = METHOD_ANNOTATION
			.replace(".", "/");
	/**
	 * Lt5750/module/log/annotation/ImportantLog;
	 */
	public static final String ASM_METHOD_ANNOTATION = "L"
			+ PATH_METHOD_ANNOTATION + ";";
}