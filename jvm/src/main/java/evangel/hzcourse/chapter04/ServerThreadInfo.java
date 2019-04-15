package evangel.hzcourse.chapter04;

import java.util.Map;

/**
 * 服务器线程信息：查看线程状况
 */
public class ServerThreadInfo {
	public static void main(String[] args) {
		for (Map.Entry<Thread, StackTraceElement[]> stackTrace : Thread
				.getAllStackTraces().entrySet()) {
			Thread thread = (Thread) stackTrace.getKey();
			StackTraceElement[] stack = (StackTraceElement[]) stackTrace
					.getValue();
			if (thread.equals(Thread.currentThread())) {
				continue;
			}
			System.out.println("\n线程：" + thread.getName() + "\n");
			for (StackTraceElement element : stack) {
				System.out.print("\t" + element + "\n");
			}
		}
	}
}
