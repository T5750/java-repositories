package t5750.utils.gson;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import t5750.utils.util.Global;

/**
 * Convert String to JsonObject with Gson
 */
public class ConvertStringToJsonObjectTest {
	/**
	 * 3. Using JsonParser
	 */
	@Test
	public void jsonParser() throws Exception {
		String json = "{ \"name\": \"" + Global.T5750 + "\", \"java\": true }";
		JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
		Assert.assertTrue(jsonObject.isJsonObject());
		Assert.assertTrue(
				jsonObject.get("name").getAsString().equals(Global.T5750));
		Assert.assertTrue(jsonObject.get("java").getAsBoolean() == true);
	}

	/**
	 * 4. Using fromJson
	 */
	@Test
	public void fromJson() throws Exception {
		String json = "{ \"name\": \"" + Global.T5750 + "\", \"java\": true }";
		JsonObject convertedObject = new Gson().fromJson(json,
				JsonObject.class);
		Assert.assertTrue(convertedObject.isJsonObject());
		Assert.assertTrue(
				convertedObject.get("name").getAsString().equals(Global.T5750));
		Assert.assertTrue(convertedObject.get("java").getAsBoolean() == true);
	}
}