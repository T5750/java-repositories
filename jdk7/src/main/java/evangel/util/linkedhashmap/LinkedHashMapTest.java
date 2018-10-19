package evangel.util.linkedhashmap;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LinkedHashMapTest {
	public static void main(String[] args) {
		test();
		testNext();
	}

	private static void test() {
		Map<String, Integer> map = new LinkedHashMap<String, Integer>();
		map.put("1", 1);
		map.put("2", 2);
		map.put("3", 3);
		map.put("4", 4);
		map.put("5", 5);
		System.out.println(map.toString());
	}

	private static void testNext() {
		// 第三个参数用于指定accessOrder值
		Map<String, String> linkedHashMap = new LinkedHashMap<>(16, 0.75f, true);
		linkedHashMap.put("name1", "josan1");
		linkedHashMap.put("name2", "josan2");
		linkedHashMap.put("name3", "josan3");
		System.out.println("开始时顺序：");
		Set<Map.Entry<String, String>> set = linkedHashMap.entrySet();
		Iterator<Map.Entry<String, String>> iterator = set.iterator();
		while (iterator.hasNext()) {
			printEntry(iterator);
		}
		System.out.println("通过get方法，导致key为name1对应的Entry到表尾");
		linkedHashMap.get("name1");
		Set<Map.Entry<String, String>> set2 = linkedHashMap.entrySet();
		Iterator<Map.Entry<String, String>> iterator2 = set2.iterator();
		while (iterator2.hasNext()) {
			printEntry(iterator2);
		}
	}

	private static void printEntry(Iterator<Map.Entry<String, String>> iterator) {
		Map.Entry entry = iterator.next();
		String key = (String) entry.getKey();
		String value = (String) entry.getValue();
		System.out.println("key:" + key + ",value:" + value);
	}
}
