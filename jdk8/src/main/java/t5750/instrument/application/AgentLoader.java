package t5750.instrument.application;

import java.io.File;
import java.util.Optional;

import com.sun.tools.attach.VirtualMachine;

public class AgentLoader {
	public static void run(String[] args) {
		// String agentFilePath =
		// "/home/adi/Desktop/agent-1.0.0-jar-with-dependencies.jar";
		String agentFilePath = args[1];
		String applicationName = "MyAtmApplication";
		System.out.println("Start " + applicationName + " first");
		// iterate all jvms and get the first one that matches our application
		// name
		Optional<String> jvmProcessOpt = Optional
				.ofNullable(VirtualMachine.list().stream().filter(jvm -> {
					System.out.println("jvm: " + jvm.displayName());
					return jvm.displayName().contains(applicationName);
				}).findFirst().get().id());
		if (!jvmProcessOpt.isPresent()) {
			System.out.println("Target Application not found");
			return;
		}
		File agentFile = new File(agentFilePath);
		try {
			String jvmPid = jvmProcessOpt.get();
			System.out.println("Attaching to target JVM with PID: " + jvmPid);
			VirtualMachine jvm = VirtualMachine.attach(jvmPid);
			jvm.loadAgent(agentFile.getAbsolutePath());
			jvm.detach();
			System.out.println(
					"Attached to target JVM and loaded Java agent successfully");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}