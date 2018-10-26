package evangel.util.hashmap;

import java.util.HashMap;

/**
 * 装载因子与性能测试<br/>
 * https://blog.csdn.net/raylee2007/article/details/50453077
 */
public class HashMapResizeTest {
	public static void main(String[] args) {
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			map.put(i, "a");
		}
		long endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
		System.out.println("---------------");
		HashMap<Integer, String> map2 = new HashMap<Integer, String>(100000, 1f);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			map2.put(i, "a");
		}
		endTime = System.currentTimeMillis();
		System.out.println(endTime - startTime);
	}
}
