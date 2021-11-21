package t5750.jdk8;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class NashornJavaScript {
	public static void main(String args[]) {
		ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
		ScriptEngine nashorn = scriptEngineManager.getEngineByName("nashorn");
		String name = "Mahesh";
		Integer result = null;
		try {
			nashorn.eval("print('" + name + "')");
			result = (Integer) nashorn.eval("10 + 2");

		} catch (ScriptException e) {
			System.out.println("Error executing script: " + e.getMessage());
		}
		System.out.println(result.toString());
	}
}