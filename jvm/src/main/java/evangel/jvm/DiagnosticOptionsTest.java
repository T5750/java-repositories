package evangel.jvm;

import java.util.List;

import sun.management.HotSpotDiagnostic;

import com.sun.management.VMOption;

/**
 * jinfo命令详解<br/>
 * https://www.jianshu.com/p/c321d0808a1b
 */
public class DiagnosticOptionsTest {
	/**
	 * java -XX:+PrintFlagsInitial | grep manageable<br/>
	 * java -XX:+PrintFlagsInitial | findstr manageable
	 */
	public static void main(String[] args) {
		HotSpotDiagnostic mxBean = new HotSpotDiagnostic();
		List<VMOption> diagnosticVMOptions = mxBean.getDiagnosticOptions();
		for (VMOption vmOption : diagnosticVMOptions) {
			System.out
					.println(vmOption.getName() + " = " + vmOption.getValue());
		}
	}
}