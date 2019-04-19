package evangel.hzcourse.chapter09.classloading;

import java.io.FileInputStream;
import java.io.InputStream;

/**
 * copy from testClassLoading.jsp
 */
public class TestClassLoading {
	public static void main(String[] args) {
		try {
			InputStream is = new FileInputStream("c:/TestClass.class");
			byte[] b = new byte[is.available()];
			is.read(b);
			is.close();
			System.out.println("<textarea style='width:1000;height=800'>");
			System.out.println(JavaClassExecuter.execute(b));
			System.out.println("</textarea>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
