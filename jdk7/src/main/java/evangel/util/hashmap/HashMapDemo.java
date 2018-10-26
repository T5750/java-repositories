package evangel.util.hashmap;

import java.util.HashMap;

/**
 * HashMapDemo
 */
public class HashMapDemo {
	public static void main(String[] args) {
		HashMap<String, String> map = new HashMap<String, String>(4);
		// 当key为空的时候，通过putForNullKey(V value)方法，把元素放到最开始的位置
		map.put(null, "1");
		map.put("key", "2");
		map.put("key", "3");
		// getForNullKey()
		System.out.println("null=" + map.get(null));
		System.out.println("key=" + map.get("key"));
	}
}
