package evangel.util.concurrent.blockingqueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 实现生产者消费者模式<br/>
 * https://segmentfault.com/a/1190000016260650
 */
public class BlockingQueueProducerConsumer {
	class Producer implements Runnable {
		// 库存队列
		private BlockingQueue<String> stock;
		// 生产/消费延迟
		private int timeOut;
		private String name;

		public Producer(BlockingQueue<String> stock, int timeout, String name) {
			this.stock = stock;
			this.timeOut = timeout;
			this.name = name;
		}

		@Override
		public void run() {
			while (true) {
				try {
					stock.put(name);
					System.out.println(name + " 正在生产数据" + " -- 库存剩余："
							+ stock.size());
					TimeUnit.MILLISECONDS.sleep(timeOut);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	class Consumer implements Runnable {
		// 库存队列
		private BlockingQueue<String> stock;
		private String consumerName;

		public Consumer(BlockingQueue<String> stock, String name) {
			this.stock = stock;
			this.consumerName = name;
		}

		@Override
		public void run() {
			while (true) {
				try {
					// 从库存消费一台电脑
					String takeName = stock.take();
					System.out.println(consumerName + " 正在消费数据：" + takeName
							+ " -- 库存剩余：" + stock.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static void main(String[] args) {
		final BlockingQueueProducerConsumer producerConsumer = new BlockingQueueProducerConsumer();
		// 定义最大库存为10
		BlockingQueue<String> stock = new ArrayBlockingQueue<>(10);
		Thread macProducer = new Thread(producerConsumer.new Producer(stock,
				500, "Mac"));
		Thread dellProducer = new Thread(producerConsumer.new Producer(stock,
				500, "Dell"));
		Thread consumer1 = new Thread(producerConsumer.new Consumer(stock,
				"zhangsan"));
		Thread consumer2 = new Thread(
				producerConsumer.new Consumer(stock, "李四"));
		macProducer.start();
		dellProducer.start();
		consumer1.start();
		consumer2.start();
	}
}
