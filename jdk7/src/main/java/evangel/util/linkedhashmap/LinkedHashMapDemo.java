package evangel.util.linkedhashmap;

import java.util.LinkedHashMap;

/**
 * 特性：（1）按照put 的属性存储（2）可以通过设置构造函数实现LRU算法 ，就是访问越少的放在越前面<br/>
 * https://blog.csdn.net/raylee2007/article/details/50458480
 */
public class LinkedHashMapDemo {
	public static void main(String[] args) {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		for (int i = 0; i < 6; i++) {
			map.put(i + "", i + "");
		}
		System.out.println("map:" + map);
		LinkedHashMap<String, String> map2 = new LinkedHashMap<String, String>(
				16, 0.75f, true);
		map2.putAll(map);
		System.out.println("map2:" + map2);
		for (int i = 0; i < 2; i++) {
			map2.get(i + "");
		}
		System.out.println("map2:" + map2);
	}
}
