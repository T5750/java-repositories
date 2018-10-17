package evangel.util.concurrent.countdownlatch;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * 主启动类，它负责初始化闭锁，然后等待，直到所有服务都被检测完。
 */
public class ApplicationStartupUtil {
	// List of service checkers
	private static List<BaseHealthChecker> _services;
	// This latch will be used to wait on
	private static CountDownLatch _latch;

	private ApplicationStartupUtil() {
	}

	private final static ApplicationStartupUtil INSTANCE = new ApplicationStartupUtil();

	public static ApplicationStartupUtil getInstance() {
		return INSTANCE;
	}

	public static boolean checkExternalServices() throws Exception {
		// Initialize the latch with number of service checkers
		_latch = new CountDownLatch(3);
		// All add checker in lists
		_services = new ArrayList<BaseHealthChecker>();
		_services.add(new NetworkHealthChecker(_latch));
		_services.add(new CacheHealthChecker(_latch));
		_services.add(new DatabaseHealthChecker(_latch));
		// Start service checkers using executor framework
		Executor executor = Executors.newFixedThreadPool(_services.size());
		for (final BaseHealthChecker v : _services) {
			executor.execute(v);
		}
		// Now wait till all services are checked
		_latch.await();
		// Services are file and now proceed startup
		for (final BaseHealthChecker v : _services) {
			if (!v.isServiceUp()) {
				return false;
			}
		}
		return true;
	}
}