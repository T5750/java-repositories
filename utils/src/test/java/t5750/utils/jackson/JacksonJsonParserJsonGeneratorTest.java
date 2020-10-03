package t5750.utils.jackson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;
import t5750.utils.jackson.entity.Address;
import t5750.utils.jackson.entity.Person;
import t5750.utils.util.Global;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonParserJsonGeneratorTest {
	private final String PATHNAME = "src/test/resources/infoOne.json";

	@Test
	public void writeJSONWithJsonGeneratorOne() throws IOException {
		final JsonFactory jsonFactory = new JsonFactory();
		final FileOutputStream file = new FileOutputStream(new File(PATHNAME));
		final JsonGenerator jsonGen = jsonFactory.createJsonGenerator(file,
				JsonEncoding.UTF8);
		final Address address = new Address("Dhananjaypur", "Varanasi", "UP");
		final Person person = new Person(1, Global.T5750, address);
		jsonGen.setCodec(new ObjectMapper());
		jsonGen.writeObject(person);
		System.out.println("Done");
	}

	@Test
	public void writeJSONWithJsonGeneratorTwo() throws IOException {
		final JsonFactory jsonFactory = new JsonFactory();
		FileOutputStream file = new FileOutputStream(new File(PATHNAME));
		JsonGenerator jsonGen = jsonFactory.createJsonGenerator(file,
				JsonEncoding.UTF8);
		Address address = new Address("Dhananjaypur", "Varanasi", "UP");
		Person person = new Person(1, Global.T5750, address);
		jsonGen.setCodec(new ObjectMapper());
		jsonGen.setPrettyPrinter(new DefaultPrettyPrinter());
		jsonGen.writeObject(person);
		System.out.println("Done");
	}

	@Test
	public void writeJSONWithJsonGeneratorThree() throws IOException {
		final JsonFactory jsonFactory = new JsonFactory();
		FileOutputStream file = new FileOutputStream(new File(PATHNAME));
		JsonGenerator jsonGen = jsonFactory.createJsonGenerator(file,
				JsonEncoding.UTF8);
		jsonGen.setPrettyPrinter(new DefaultPrettyPrinter());
		jsonGen.writeStartObject();
		jsonGen.writeNumberField("id", 1);
		jsonGen.writeStringField("country", "China");
		jsonGen.writeFieldName("states");
		jsonGen.writeStartArray();
		jsonGen.writeString("UP");
		jsonGen.writeString("MP");
		jsonGen.writeEndArray();
		jsonGen.writeEndObject();
		jsonGen.close();
		System.out.println("Done");
	}

	@Test
	public void readJSONWithJsonParser() throws IOException {
		JsonFactory jsonFactory = new JsonFactory();
		JsonParser jp = jsonFactory.createJsonParser(new File(PATHNAME));
		jp.setCodec(new ObjectMapper());
		JsonNode jsonNode = jp.readValueAsTree();
		readJsonData(jsonNode);
	}

	static void readJsonData(JsonNode jsonNode) {
		Iterator<Map.Entry<String, JsonNode>> ite = jsonNode.fields();
		while (ite.hasNext()) {
			Map.Entry<String, JsonNode> entry = ite.next();
			if (entry.getValue().isObject()) {
				readJsonData(entry.getValue());
			} else {
				System.out.println("key:" + entry.getKey() + ", value:"
						+ entry.getValue());
			}
		}
	}
}
