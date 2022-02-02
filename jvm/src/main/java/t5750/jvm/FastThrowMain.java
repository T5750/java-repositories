package t5750.jvm;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 异常栈信息不见了之JVM参数OmitStackTraceInFastThrow<br/>
 * https://www.jianshu.com/p/cc1bd35466cb
 */
public class FastThrowMain {
	// -XX:-OmitStackTraceInFastThrow
	public static void main(String[] args) throws InterruptedException {
		WithNPE withNPE = new WithNPE();
		ExecutorService executorService = Executors.newFixedThreadPool(20);
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			executorService.execute(withNPE);
			// 稍微sleep一下, 是为了不要让异常抛出太快, 导致控制台输出太快, 把有异常栈信息冲掉, 只留下fast
			// throw方式抛出的异常
			Thread.sleep(2);
		}
	}
}

class WithNPE extends Thread {
	private static int count = 0;

	@Override
	public void run() {
		try {
			System.out.println(this.getClass().getSimpleName() + "--"
					+ (++count));
			String str = null;
			// 制造空指针NPE
			System.out.println(str.length());
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
}
