package evangel.util.concurrent.locks.condition;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 一个lock对象可以有多个Condition类,Condition类负责对lock对象进行wait,notify,notifyall操作<br/>
 * https://segmentfault.com/a/1190000016260650
 */
public class ConditionProducerConsumer {
	final Lock lock = new ReentrantLock();
	final Condition notFull = lock.newCondition();
	final Condition notEmpty = lock.newCondition();
	// 加入库存概念，可批量生产和消费
	// 定义最大库存为10
	final String[] stock = new String[10];
	// 写入标记、读取标记、已有商品数量
	int putptr, takeptr, count;

	public void put(String computer) {
		// lock代替synchronized
		lock.lock();
		try {
			// 若库存已满则生产者线程阻塞
			while (count == stock.length) {
				notFull.await();
			}
			// 库存中加入商品
			stock[putptr] = computer;
			// 库存已满，指针置零，方便下次重新写入
			if (++putptr == stock.length) {
				putptr = 0;
			}
			++count;
			System.out.println(computer + " 正在生产数据： -- 库存剩余：" + count);
			notEmpty.signal();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String take(String consumerName) {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			// 从库存中获取商品
			String computer = stock[takeptr];
			if (++takeptr == stock.length) {
				takeptr = 0;
			}
			--count;
			System.out.println(consumerName + " 正在消费数据：" + computer
					+ " -- 库存剩余：" + count);
			notFull.signal();
			return computer;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		// 无逻辑作用，放慢速度
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static void main(String[] args) {
		final ConditionProducerConsumer computer = new ConditionProducerConsumer();
		Thread dellProducer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					computer.put("Dell");
				}
			}
		});
		Thread macProducer = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					computer.put("Mac");
				}
			}
		});
		Thread consumer1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					computer.take("zhangsan");
				}
			}
		});
		Thread consumer2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					computer.take("李四");
				}
			}
		});
		// 两个生产者两个消费者同时运行
		dellProducer.start();
		macProducer.start();
		consumer1.start();
		consumer2.start();
	}
}
