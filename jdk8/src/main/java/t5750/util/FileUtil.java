package t5750.util;

import java.io.FileOutputStream;

public class FileUtil {
	public static void write(byte[] b, String name) {
		try {
			FileOutputStream out = new FileOutputStream(name);
			out.write(b);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}