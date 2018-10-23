package evangel.keyword.atomicoperation;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 所谓令牌桶限流器，就是系统以恒定的速度向桶内增加令牌。每次请求前从令牌桶里面获取令牌。如果获取到令牌就才可以进行访问。当令牌桶内没有令牌的时候，
 * 拒绝提供服务。我们来看看 eureka 的限流器是如何使用 CAS 来维护多线程环境下对 token 的增加和分发的。
 */
public class RateLimiter {
	/**
	 * 速率单位转换成毫秒
	 */
	private final long rateToMsConversion;
	/**
	 * 消耗令牌数
	 */
	private final AtomicInteger consumedTokens = new AtomicInteger();
	/**
	 * 最后填充令牌的时间
	 */
	private final AtomicLong lastRefillTime = new AtomicLong(0);

	@Deprecated
	public RateLimiter() {
		this(TimeUnit.SECONDS);
	}

	public RateLimiter(TimeUnit averageRateUnit) {
		switch (averageRateUnit) {
		case SECONDS:
			rateToMsConversion = 1000;
			break;
		case MINUTES:
			rateToMsConversion = 60 * 1000;
			break;
		default:
			throw new IllegalArgumentException("TimeUnit of " + averageRateUnit
					+ " is not supported");
		}
	}

	/**
	 * 获取令牌( Token )
	 *
	 * @param burstSize
	 *            令牌桶上限
	 * @param averageRate
	 *            令牌再装平均速率
	 * @return 是否获取成功
	 */
	public boolean acquire(int burstSize, long averageRate) {
		return acquire(burstSize, averageRate, System.currentTimeMillis());
	}

	public boolean acquire(int burstSize, long averageRate,
			long currentTimeMillis) {
		// Instead of throwing exception, we just let all the traffic go
		if (burstSize <= 0 || averageRate <= 0) {
			return true;
		}
		// 添加token
		refillToken(burstSize, averageRate, currentTimeMillis);
		// 消费token
		return consumeToken(burstSize);
	}

	private void refillToken(int burstSize, long averageRate,
			long currentTimeMillis) {
		// 获得 最后填充令牌的时间
		long refillTime = lastRefillTime.get();
		// 获得 过去多少毫秒
		long timeDelta = currentTimeMillis - refillTime;
		// 计算 可填充最大令牌数量
		long newTokens = timeDelta * averageRate / rateToMsConversion;
		if (newTokens > 0) {
			// 计算 新的填充令牌的时间
			long newRefillTime = refillTime == 0 ? currentTimeMillis
					: refillTime + newTokens * rateToMsConversion / averageRate;
			// CAS 保证有且仅有一个线程进入填充
			if (lastRefillTime.compareAndSet(refillTime, newRefillTime)) {
				while (true) {
					// 计算 填充令牌后的已消耗令牌数量
					int currentLevel = consumedTokens.get();
					// In case burstSize decreased
					int adjustedLevel = Math.min(currentLevel, burstSize);
					int newLevel = (int) Math.max(0, adjustedLevel - newTokens);
					// while true 直到更新成功为止
					if (consumedTokens.compareAndSet(currentLevel, newLevel)) {
						return;
					}
				}
			}
		}
	}

	private boolean consumeToken(int burstSize) {
		while (true) {
			int currentLevel = consumedTokens.get();
			if (currentLevel >= burstSize) {
				return false;
			}
			// while true 直到没有token 或者 获取到为止
			if (consumedTokens.compareAndSet(currentLevel, currentLevel + 1)) {
				return true;
			}
		}
	}

	public void reset() {
		consumedTokens.set(0);
		lastRefillTime.set(0);
	}

	public static void main(String[] args) {
		final RateLimiter rateLimiter = new RateLimiter(TimeUnit.SECONDS);
		final int burstSize = 10;
		final long averageRate = 20000;
		boolean result = rateLimiter.acquire(burstSize, averageRate);
		try {
			for (int i = 0; i < 10; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						boolean result = rateLimiter.acquire(burstSize,
								averageRate);
						System.out.println("consumedTokens="
								+ rateLimiter.consumedTokens.get()
								+ " lastRefillTime="
								+ rateLimiter.lastRefillTime.get() + " result="
								+ result + " "
								+ Thread.currentThread().getName());
					}
				}).start();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("consumedTokens=" + rateLimiter.consumedTokens.get()
				+ " lastRefillTime=" + rateLimiter.lastRefillTime.get()
				+ " result=" + result + " " + Thread.currentThread().getName());
	}
}
