package t5750.utils.testcontainers;

import io.lettuce.core.RedisClient;
import io.lettuce.core.api.StatefulRedisConnection;

/**
 * https://github.com/testcontainers/testcontainers-java/blob/master/docs/examples/junit4/redis/src/main/java/quickstart/RedisBackedCache.java
 */
public class RedisBackedCache {
	private final StatefulRedisConnection<String, String> connection;

	public RedisBackedCache(String hostname, Integer port) {
		RedisClient client = RedisClient
				.create(String.format("redis://%s:%d/0", hostname, port));
		connection = client.connect();
	}

	public String get(String key) {
		return connection.sync().get(key);
	}

	public void put(String key, String value) {
		connection.sync().set(key, value);
	}
}