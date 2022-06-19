package t5750.jvm;

/**
 * https://blog.csdn.net/renfufei/article/details/78061354
 * </p>
 * -XX:MaxMetaspaceSize=16m -XX:+PrintGCDetails -XX:+PrintGCTimeStamps
 * -XX:+PrintHeapAtGC
 * </p>
 * -XX:MaxMetaspaceSize=16m -XX:+TraceClassLoading -XX:+TraceClassUnloading
 */
public class MetaspaceOOMTest {
	static javassist.ClassPool cp = javassist.ClassPool.getDefault();

	public static void main(String[] args) throws Exception {
		for (int i = 0;; i++) {
			Class c = cp.makeClass("eu.plumbr.demo.Generated" + i).toClass();
		}
	}
}
