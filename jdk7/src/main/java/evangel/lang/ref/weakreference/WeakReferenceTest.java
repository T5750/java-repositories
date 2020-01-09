package evangel.lang.ref.weakreference;

import java.lang.ref.WeakReference;

/**
 * WeakReference的理解与使用 https://www.tuicool.com/articles/imyueq
 */
public class WeakReferenceTest {
	public static void main(String[] args) {
		// 只要car还指向car object, car object就不会被回收
		Car car = new Car(22000, "silver");
		// WeakReference的一个特点是它何时被回收是不可确定的, 因为这是由GC运行的不确定性所确定的.
		// 所以, 一般用WeakReference引用的对象是有价值被cache, 而且很容易被重新构建, 且很消耗内存的对象.
		WeakReference<Car> weakCar = new WeakReference<Car>(car);
		int i = 0;
		while (true) {
			// System.out.println("here is the strong reference 'car' " + car);
			if (weakCar.get() != null) {
				i++;
				System.out.println("Object is alive for " + i + " loops - "
						+ weakCar);
			} else {
				System.out.println("Object has been collected.");
				break;
			}
		}
	}
}