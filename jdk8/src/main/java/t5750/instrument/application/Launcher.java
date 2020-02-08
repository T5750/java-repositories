package t5750.instrument.application;

public class Launcher {
	public static void main(String[] args) throws Exception {
		if (args.length > 0) {
			if (args[0].equals("StartMyAtmApplication")) {
				new MyAtmApplication().run(args);
			} else if (args[0].equals("LoadAgent")) {
				new AgentLoader().run(args);
			}
		} else {
			System.out.println("args and VM options");
		}
	}
}