package t5750.utils.jackson;

import java.util.Date;

import org.junit.Test;
import t5750.utils.jackson.entity.Writer;
import t5750.utils.jackson.enums.Code;
import t5750.utils.util.Global;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonJsonFormatTest {
	@Test
	public void testObjectToJSON() throws JsonProcessingException {
		final Writer writer = new Writer(Global.T5750, new Date(), new Date());
		final ObjectMapper mapper = new ObjectMapper();
		final String jsonData = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(writer);
		System.out.println(jsonData);
	}

	@Test
	public void testJSONToObject() throws JsonProcessingException {
		final String jsonData = "{" + "\"writerName\" : \"T5750\","
				+ "\"bdate\" : 1528703231286,"
				+ "\"pubDate\" : \"2020-十月-03 09:01:59 GMT+08:00\"" + "}";
		final ObjectMapper mapper = new ObjectMapper();
		final Writer writer = mapper.readValue(jsonData, Writer.class);
		System.out.println(writer.getName());
		System.out.println(
				writer.getBirthDate() + " | " + writer.getRecentBookPubDate());
	}

	@Test
	public void testEnum() throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		System.out.println(mapper.writeValueAsString(Code.BLOCKING));
		System.out.println(mapper.writeValueAsString(Code.CRITICAL));
	}
}
