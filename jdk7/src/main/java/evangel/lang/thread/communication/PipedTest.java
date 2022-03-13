package evangel.lang.thread.communication;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 管道通信
 */
public class PipedTest {
	private static final Logger LOGGER = LogManager
			.getLogger(PipedTest.class);

	public static void piped() throws IOException {
		// 面向于字符 PipedInputStream 面向于字节
		final PipedWriter writer = new PipedWriter();
		final PipedReader reader = new PipedReader();
		// 输入输出流建立连接
		writer.connect(reader);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("running");
				try {
					for (int i = 0; i < 10; i++) {
						writer.write(i + "");
						Thread.sleep(10);
					}
				} catch (Exception e) {
				} finally {
					try {
						writer.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				LOGGER.info("running2");
				int msg = 0;
				try {
					while ((msg = reader.read()) != -1) {
						LOGGER.info("msg=" + msg);
					}
				} catch (Exception e) {
				}
			}
		});
		t1.start();
		t2.start();
	}

	public static void main(String[] args) {
		try {
			piped();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
