package evangel.bjsxt.base001;

import java.util.Vector;

public class Test03 {
	public static void main(String[] args) {
		// -Xms4m -Xmx4m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=d:/Test03.dump
		// 堆内存溢出
		Vector v = new Vector();
		for (int i = 0; i < 5; i++) {
			v.add(new Byte[1 * 1024 * 1024]);
		}
	}
}