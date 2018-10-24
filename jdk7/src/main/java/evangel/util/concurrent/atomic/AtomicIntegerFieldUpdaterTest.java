package evangel.util.concurrent.atomic;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * 如果我们只需要某个类里的某个字段，那么就需要使用原子更新字段类<br/>
 * 原子更新字段类都是抽象类，每次使用都时候必须使用静态方法newUpdater创建一个更新器。原子更新类的字段的必须使用public
 * volatile修饰符。
 */
public class AtomicIntegerFieldUpdaterTest {
	private static AtomicIntegerFieldUpdater<User> a = AtomicIntegerFieldUpdater
			.newUpdater(User.class, "old");

	public static void main(String[] args) {
		User conan = new User("conan", 10);
		System.out.println(a.getAndIncrement(conan));
		System.out.println(a.get(conan));
	}

	public static class User {
		private String name;
		public volatile int old;

		public User(String name, int old) {
			this.name = name;
			this.old = old;
		}

		public String getName() {
			return name;
		}

		public int getOld() {
			return old;
		}
	}
}
