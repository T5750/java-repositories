package t5750.utils.jackson;

import java.io.IOException;
import java.util.function.Consumer;

import org.junit.Test;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonNodeForEachTest {
	@Test
	public void iterateJSONwithConsumer() throws IOException {
		final JsonFactory jsonFactory = new JsonFactory();
		final String jsonData = "{" + "\"writerName\" : \"T5750\","
				+ "\"bdate\" : 1528703231286,"
				+ "\"pubDate\" : \"2020-十月-03 09:01:59 GMT+08:00\"" + "}";
		final JsonParser jp = jsonFactory.createJsonParser(jsonData);
		jp.setCodec(new ObjectMapper());
		final JsonNode jsonNode = jp.readValueAsTree();
		Consumer<JsonNode> data = (JsonNode node) -> System.out
				.println(node.asText());
		jsonNode.forEach(data);
	}
}
