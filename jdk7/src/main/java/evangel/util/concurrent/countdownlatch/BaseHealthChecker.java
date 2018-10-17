package evangel.util.concurrent.countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 负责所有特定的外部服务健康的检测。它删除了重复的代码和闭锁的中心控制代码。
 */
public abstract class BaseHealthChecker implements Runnable {
	private CountDownLatch _latch;
	private String _serviceName;
	private boolean _serviceUp;

	// Get latch object in constructor so that after completing the task, thread
	// can countDown() the latch
	public BaseHealthChecker(String serviceName, CountDownLatch latch) {
		super();
		this._latch = latch;
		this._serviceName = serviceName;
		this._serviceUp = false;
	}

	@Override
	public void run() {
		try {
			verifyService();
			_serviceUp = true;
		} catch (Throwable t) {
			t.printStackTrace(System.err);
			_serviceUp = false;
		} finally {
			if (_latch != null) {
				_latch.countDown();
			}
		}
	}

	public String getServiceName() {
		return _serviceName;
	}

	public boolean isServiceUp() {
		return _serviceUp;
	}

	// This methos needs to be implemented by all specific service checker
	public abstract void verifyService();
}