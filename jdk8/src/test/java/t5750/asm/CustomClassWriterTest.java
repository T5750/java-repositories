package t5750.asm;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import t5750.util.FileUtil;
import t5750.util.Global;

/**
 * 4. Working With the Event-based ASM API
 */
public class CustomClassWriterTest {
	private CustomClassWriter customClassWriter;

	@Before
	public void setup() {
		customClassWriter = new CustomClassWriter();
	}

	/**
	 * 4.1. Working With Fields
	 */
	@Test
	public void addField() throws Exception {
		byte[] b = customClassWriter.addField();
		String integerClass = Global.T5750 + File.separator + "Integer.class";
		FileUtil.write(b, integerClass);
	}

	/**
	 * 4.2. Working With Methods
	 */
	@Test
	public void publicizeMethod() throws Exception {
		customClassWriter.publicizeMethod();
	}

	/**
	 * 4.3. Working With Classes
	 */
	@Test
	public void addInterface() throws Exception {
		customClassWriter.addInterface();
	}
}