package t5750.utils.testcontainers;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * JUnit 4 Quickstart<br/>
 * https://www.testcontainers.org/quickstart/junit_4_quickstart/
 */
public class RedisBackedCacheIntTest {
	private RedisBackedCache underTest;
	// rule {
	@Rule
	public GenericContainer redis = new GenericContainer(
			DockerImageName.parse("redis:5")).withExposedPorts(6379);
	// }

	@Before
	public void setUp() {
		String address = redis.getHost();
		Integer port = redis.getFirstMappedPort();
		// Now we have an address and port for Redis, no matter where it is
		// running
		underTest = new RedisBackedCache(address, port);
	}

	@Test
	public void testSimplePutAndGet() {
		underTest.put("test", "example");
		Object retrieved = underTest.get("test");
		assertEquals("example", retrieved);
	}
}
