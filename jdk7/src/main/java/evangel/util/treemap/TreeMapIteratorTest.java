package evangel.util.treemap;

import java.util.*;

/**
 * 遍历TreeMap的测试程序。 <br/>
 * (01) 通过entrySet()去遍历key、value，参考实现函数： iteratorTreeMapByEntryset() <br/>
 * (02) 通过keySet()去遍历key、value，参考实现函数： iteratorTreeMapByKeyset() <br/>
 * (03) 通过values()去遍历value，参考实现函数： iteratorTreeMapJustValues()
 */
public class TreeMapIteratorTest {
	public static void main(String[] args) {
		int val = 0;
		String key = null;
		Integer value = null;
		Random r = new Random();
		TreeMap map = new TreeMap();
		for (int i = 0; i < 12; i++) {
			// 随机获取一个[0,100)之间的数字
			val = r.nextInt(100);
			key = String.valueOf(val);
			value = r.nextInt(5);
			// 添加到TreeMap中
			map.put(key, value);
			System.out.println(" key:" + key + " value:" + value);
		}
		long start = System.currentTimeMillis();
		// 通过entrySet()遍历TreeMap的key-value
		iteratorTreeMapByEntryset(map);
		long endByEntryset = System.currentTimeMillis();
		System.out.println("during=" + (endByEntryset - start));
		// 通过keySet()遍历TreeMap的key-value
		iteratorTreeMapByKeyset(map);
		long endByKeyset = System.currentTimeMillis();
		System.out.println("during=" + (endByKeyset - endByEntryset));
		// 单单遍历TreeMap的value
		iteratorTreeMapJustValues(map);
		long endJustValues = System.currentTimeMillis();
		System.out.println("during=" + (endJustValues - endByKeyset));
	}

	/*
	 * 通过entry set遍历TreeMap 效率高!
	 */
	private static void iteratorTreeMapByEntryset(TreeMap map) {
		if (map == null) {
			return;
		}
		System.out.println("\niterator TreeMap By entryset");
		String key = null;
		Integer integ = null;
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			key = (String) entry.getKey();
			integ = (Integer) entry.getValue();
			System.out.println(key + " -- " + integ.intValue());
		}
	}

	/*
	 * 通过keyset来遍历TreeMap 效率低!
	 */
	private static void iteratorTreeMapByKeyset(TreeMap map) {
		if (map == null) {
			return;
		}
		System.out.println("\niterator TreeMap By keyset");
		String key = null;
		Integer integ = null;
		Iterator iter = map.keySet().iterator();
		while (iter.hasNext()) {
			key = (String) iter.next();
			integ = (Integer) map.get(key);
			System.out.println(key + " -- " + integ.intValue());
		}
	}

	/*
	 * 遍历TreeMap的values
	 */
	private static void iteratorTreeMapJustValues(TreeMap map) {
		if (map == null) {
			return;
		}
		System.out.println("\niterator TreeMap By values");
		Collection c = map.values();
		Iterator iter = c.iterator();
		while (iter.hasNext()) {
			System.out.println(iter.next());
		}
	}
}