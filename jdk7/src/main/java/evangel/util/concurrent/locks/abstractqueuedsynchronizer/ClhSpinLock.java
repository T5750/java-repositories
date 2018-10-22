package evangel.util.concurrent.locks.abstractqueuedsynchronizer;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 通常就是用CLH lock queue来实现自旋锁，所谓自旋锁简单来说就是线程通过循环来等待而不是睡眠。
 */
public class ClhSpinLock {
	private final ThreadLocal<Node> prev;
	private final ThreadLocal<Node> node;
	private final AtomicReference<Node> tail = new AtomicReference<Node>(
			new Node());

	public ClhSpinLock() {
		this.node = new ThreadLocal<Node>() {
			@Override
			protected Node initialValue() {
				return new Node();
			}
		};
		this.prev = new ThreadLocal<Node>() {
			@Override
			protected Node initialValue() {
				return null;
			}
		};
	}

	public void lock() {
		final Node node = this.node.get();
		node.locked = true;
		// 一个CAS操作即可将当前线程对应的节点加入到队列中，并且同时获得了前继节点的引用，然后就是等待前继释放锁
		Node pred = this.tail.getAndSet(node);
		this.prev.set(pred);
		while (pred.locked) {// 进入自旋
		}
	}

	public void unlock() {
		final Node node = this.node.get();
		node.locked = false;
		this.node.set(this.prev.get());
	}

	private static class Node {
		private volatile boolean locked;
	}

	/**
	 * 上面的代码中线程巧妙的通过ThreadLocal保存了当前结点和前继结点的引用，自旋就是lock中的while循环。
	 * 总的来说这种实现的好处是保证所有等待线程的公平竞争，而且没有竞争同一个变量，因为每个线程只要等待自己的前继释放就好了。
	 * 而自旋的好处是线程不需要睡眠和唤醒，减小了系统调用的开销。
	 */
	public static void main(String[] args) {
		final ClhSpinLock lock = new ClhSpinLock();
		lock.lock();
		try {
			for (int i = 0; i < 10; i++) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						lock.lock();
						System.out.println(Thread.currentThread().getId()
								+ " acquired the lock!");
						lock.unlock();
					}
				}).start();
				Thread.sleep(100);
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("main thread unlock!");
		lock.unlock();
	}
}
