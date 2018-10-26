package evangel.util.treemap;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * 按照key来排序<br/>
 * https://blog.csdn.net/raylee2007/article/details/50458480
 */
public class TreeMapTest {
	public static void main(String[] args) {
		TreeMap<Integer, String> map = new TreeMap<Integer, String>();
		map.put(4, "4");
		map.put(2, "2");
		map.put(1, "1");
		System.out.println(map);
		System.out.println("----------------");
		// 由于TreeMap是按照key来排序，因此我们可以通过map来取得firstkey和lastkey等通过排序得到的一些结果。
		TreeMap<String, String> map2 = new TreeMap<String, String>(
				new Comparator<String>() {
					@Override
					public int compare(String o1, String o2) {
						return o1.compareTo(o2);
					}
				});
		map2.put("b", "3");
		map2.put("a", "1");
		map2.put("d", "4");
		map2.put("c", "2");
		System.out.println(map2);
		System.out.println("----------------");
		TreeMap<String, String> map3 = new TreeMap<String, String>();
		map3.put("b", "3");
		map3.put("a", "1");
		map3.put("d", "4");
		map3.put("c", "2");
		System.out.println(map3);
	}
}
