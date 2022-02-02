package t5750.hzcourse.chapter08;

/**
 *
 */
public class TestGrandFather {
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
			super.thinking();
		}
	}

	public static void main(String[] args) {
		(new TestGrandFather().new Son()).thinking();
	}
}
