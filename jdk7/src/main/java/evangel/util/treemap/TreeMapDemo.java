package evangel.util.treemap;

import java.util.*;

/**
 * TreeMap测试程序
 */
public class TreeMapDemo {
	public static void main(String[] args) {
		// 测试常用的API
		testTreeMapOridinaryAPIs();
		// 测试TreeMap的导航函数
		// testNavigableMapAPIs();
		// 测试TreeMap的子Map函数
		// testSubMapAPIs();
	}

	/**
	 * 测试常用的API
	 */
	private static void testTreeMapOridinaryAPIs() {
		// 初始化随机种子
		Random r = new Random();
		// 新建TreeMap
		TreeMap tmap = new TreeMap();
		// 添加操作
		tmap.put("one", r.nextInt(10));
		tmap.put("two", r.nextInt(10));
		tmap.put("three", r.nextInt(10));
		System.out.printf("\n ---- testTreeMapOridinaryAPIs ----\n");
		// 打印出TreeMap
		System.out.printf("%s\n", tmap);
		// 通过Iterator遍历key-value
		Iterator iter = tmap.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.printf("next : %s - %s\n", entry.getKey(),
					entry.getValue());
		}
		// TreeMap的键值对个数
		System.out.printf("size: %s\n", tmap.size());
		// containsKey(Object key) :是否包含键key
		System.out.printf("contains key two : %s\n", tmap.containsKey("two"));
		System.out.printf("contains key five : %s\n", tmap.containsKey("five"));
		// containsValue(Object value) :是否包含值value
		System.out.printf("contains value 0 : %s\n",
				tmap.containsValue(new Integer(0)));
		// remove(Object key) ： 删除键key对应的键值对
		tmap.remove("three");
		System.out.printf("tmap:%s\n", tmap);
		// clear() ： 清空TreeMap
		tmap.clear();
		// isEmpty() : TreeMap是否为空
		System.out.printf("%s\n", (tmap.isEmpty() ? "tmap is empty"
				: "tmap is not empty"));
	}

	/**
	 * 测试TreeMap的子Map函数
	 */
	public static void testSubMapAPIs() {
		// 新建TreeMap
		TreeMap tmap = new TreeMap();
		// 添加“键值对”
		tmap.put("a", 101);
		tmap.put("b", 102);
		tmap.put("c", 103);
		tmap.put("d", 104);
		tmap.put("e", 105);
		System.out.printf("\n ---- testSubMapAPIs ----\n");
		// 打印出TreeMap
		System.out.printf("tmap:\n\t%s\n", tmap);
		// 测试 headMap(K toKey)
		System.out.printf("tmap.headMap(\"c\"):\n\t%s\n", tmap.headMap("c"));
		// 测试 headMap(K toKey, boolean inclusive)
		System.out.printf("tmap.headMap(\"c\", true):\n\t%s\n",
				tmap.headMap("c", true));
		System.out.printf("tmap.headMap(\"c\", false):\n\t%s\n",
				tmap.headMap("c", false));
		// 测试 tailMap(K fromKey)
		System.out.printf("tmap.tailMap(\"c\"):\n\t%s\n", tmap.tailMap("c"));
		// 测试 tailMap(K fromKey, boolean inclusive)
		System.out.printf("tmap.tailMap(\"c\", true):\n\t%s\n",
				tmap.tailMap("c", true));
		System.out.printf("tmap.tailMap(\"c\", false):\n\t%s\n",
				tmap.tailMap("c", false));
		// 测试 subMap(K fromKey, K toKey)
		System.out.printf("tmap.subMap(\"a\", \"c\"):\n\t%s\n",
				tmap.subMap("a", "c"));
		// 测试
		System.out.printf("tmap.subMap(\"a\", true, \"c\", true):\n\t%s\n",
				tmap.subMap("a", true, "c", true));
		System.out.printf("tmap.subMap(\"a\", true, \"c\", false):\n\t%s\n",
				tmap.subMap("a", true, "c", false));
		System.out.printf("tmap.subMap(\"a\", false, \"c\", true):\n\t%s\n",
				tmap.subMap("a", false, "c", true));
		System.out.printf("tmap.subMap(\"a\", false, \"c\", false):\n\t%s\n",
				tmap.subMap("a", false, "c", false));
		// 测试 navigableKeySet()
		System.out.printf("tmap.navigableKeySet():\n\t%s\n",
				tmap.navigableKeySet());
		// 测试 descendingKeySet()
		System.out.printf("tmap.descendingKeySet():\n\t%s\n",
				tmap.descendingKeySet());
	}

	/**
	 * 测试TreeMap的导航函数
	 */
	public static void testNavigableMapAPIs() {
		// 新建TreeMap
		NavigableMap nav = new TreeMap();
		// 添加“键值对”
		nav.put("aaa", 111);
		nav.put("bbb", 222);
		nav.put("eee", 333);
		nav.put("ccc", 555);
		nav.put("ddd", 444);
		System.out.printf("\n ---- testNavigableMapAPIs ----\n");
		// 打印出TreeMap
		System.out.printf("Whole list:%s%n", nav);
		// 获取第一个key、第一个Entry
		System.out.printf("First key: %s\tFirst entry: %s%n", nav.firstKey(),
				nav.firstEntry());
		// 获取最后一个key、最后一个Entry
		System.out.printf("Last key: %s\tLast entry: %s%n", nav.lastKey(),
				nav.lastEntry());
		// 获取“小于/等于bbb”的最大键值对
		System.out.printf("Key floor before bbb: %s%n", nav.floorKey("bbb"));
		// 获取“小于bbb”的最大键值对
		System.out.printf("Key lower before bbb: %s%n", nav.lowerKey("bbb"));
		// 获取“大于/等于bbb”的最小键值对
		System.out.printf("Key ceiling after ccc: %s%n", nav.ceilingKey("ccc"));
		// 获取“大于bbb”的最小键值对
		System.out.printf("Key higher after ccc: %s%n\n", nav.higherKey("ccc"));
	}
}
