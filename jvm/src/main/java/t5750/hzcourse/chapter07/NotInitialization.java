package t5750.hzcourse.chapter07;

/**
 * 非主动使用类字段演示 <br/>
 * -XX:+TraceClassLoading
 **/
public class NotInitialization {
	public static void main(String[] args) {
		System.out.println(SubClass.value);
	}
}