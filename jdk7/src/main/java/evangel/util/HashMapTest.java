package evangel.util;

import java.util.HashMap;
import java.util.UUID;

/**
 * 1.8 HashMap 原有的问题也都存在，比如在并发场景下使用时容易出现死循环
 */
public class HashMapTest {
	public static void main(String[] args) {
		final HashMap<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < 10000; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					map.put(UUID.randomUUID().toString(), "");
				}
			}).start();
		}
	}
}
