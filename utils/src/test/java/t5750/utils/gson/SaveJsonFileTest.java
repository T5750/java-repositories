package t5750.utils.gson;

import java.io.FileWriter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import t5750.utils.gson.entity.User;

/**
 * Save Data to a JSON File with Gson
 */
public class SaveJsonFileTest {
	private static final String FILE_PATH = "target/saveJsonFile.json";
	private Gson gson;
	private FileWriter fileWriter;

	@Before
	public void setup() throws Exception {
		gson = new Gson();
		fileWriter = new FileWriter(FILE_PATH);
	}

	@After
	public void destroy() throws Exception {
		fileWriter.flush();
		fileWriter.close();
	}

	/**
	 * 3. Saving Data to a JSON File<br/>
	 * 3.1. Primitives
	 */
	@Test
	public void primitives() throws Exception {
		gson.toJson(123.45, fileWriter);
	}

	/**
	 * 3.2. Custom Objects
	 */
	@Test
	public void customObjects() throws Exception {
		User user = new User(1, "Tom Smith", "American");
		gson.toJson(user, fileWriter);
	}

	/**
	 * Gson omits null fields during serialization
	 */
	@Test
	public void customNullFields() throws Exception {
		gson.toJson(new User(1, null, "Unknown"), fileWriter);
	}

	/**
	 * 3.3. Collections
	 */
	@Test
	public void collections() throws Exception {
		User[] users = new User[] { new User(1, "Mike"), new User(2, "Tom") };
		gson.toJson(users, fileWriter);
	}

	/**
	 * 4. Using GsonBuilder
	 */
	@Test
	public void useGsonBuilder() throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		User[] users = new User[] { new User(1, "Mike"), new User(2, "Tom") };
		gson.toJson(users, fileWriter);
	}
}