package t5750.utils.gson;

import org.junit.Test;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * Convert Array to JSON Using Gson
 */
public class ArrayJsonTest {
	private static final String JSON_INPUT_OBJECT = "{\"values\":{\"itemKey\":\"ID0001\",\"itemValue\":\"123\"}}";
	private static final String JSON_INPUT_ARRAY = "{\"ts\":1628210121777,\"values\":[{\"itemKey\":\"ID0001\",\"itemValue\":\"123\"},{\"itemKey\":\"ID0002\",\"itemValue\":\"456\"}]}";

	private void fromJson(JsonObject convertedObject) {
		JsonElement valuesJsonElement = convertedObject.get("values");
		if (valuesJsonElement.isJsonObject()) {
			JsonObject valuesObject = valuesJsonElement.getAsJsonObject();
			System.out.println(valuesObject);
		} else if (valuesJsonElement.isJsonArray()) {
			System.out.println(valuesJsonElement);
			JsonArray valuesArray = valuesJsonElement.getAsJsonArray();
			for (JsonElement jsonElement : valuesArray) {
				JsonObject valuesObject = jsonElement.getAsJsonObject();
				System.out.println(valuesObject);
			}
		}
	}

	@Test
	public void fromJsonObject() throws Exception {
		JsonObject convertedObject = new Gson().fromJson(JSON_INPUT_OBJECT,
				JsonObject.class);
		fromJson(convertedObject);
	}

	@Test
	public void fromJsonArray() throws Exception {
		JsonObject convertedObject = new Gson().fromJson(JSON_INPUT_ARRAY,
				JsonObject.class);
		fromJson(convertedObject);
	}
}