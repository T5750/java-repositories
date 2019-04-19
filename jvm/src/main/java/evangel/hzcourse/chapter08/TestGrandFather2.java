package evangel.hzcourse.chapter08;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;

/**
 *
 */
public class TestGrandFather2 {
	class GrandFather {
		void thinking() {
			System.out.println("i am grandfather");
		}
	}

	class Father extends GrandFather {
		@Override
		void thinking() {
			System.out.println("i am father");
		}
	}

	class Son extends Father {
		@Override
		void thinking() {
			try {
				MethodType mt = MethodType.methodType(void.class);
				MethodHandle mh = lookup().findSpecial(GrandFather.class,
						"thinking", mt, getClass());
				mh.invoke(this);
			} catch (Throwable e) {
			}
		}
	}

	class Daughter extends Father {
		@Override
		void thinking() {
			try {
				MethodType mt = MethodType.methodType(void.class);
				Field IMPL_LOOKUP = MethodHandles.Lookup.class
						.getDeclaredField("IMPL_LOOKUP");
				IMPL_LOOKUP.setAccessible(true);
				MethodHandles.Lookup lkp = (MethodHandles.Lookup) IMPL_LOOKUP
						.get(null);
				MethodHandle mh = lkp.findSpecial(GrandFather.class,
						"thinking", mt, GrandFather.class);
				mh.invoke(this);
			} catch (Throwable e) {
			}
		}
	}

	public static void main(String[] args) {
		(new TestGrandFather2().new Son()).thinking();
		System.out.println("----------------------------------");
		(new TestGrandFather2().new Daughter()).thinking();
	}
}
