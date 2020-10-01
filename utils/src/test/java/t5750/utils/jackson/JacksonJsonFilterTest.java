package t5750.utils.jackson;

import org.junit.Test;
import t5750.utils.jackson.entity.Student;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 *
 */
public class JacksonJsonFilterTest {
	@Test
	public void testJsonFilter() throws Exception {
		final SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("studentFilter", SimpleBeanPropertyFilter
				.serializeAllExcept("stdName", "stdCity"));
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setFilterProvider(filterProvider);
		final Student student = new Student("Mohit", 30, "ABCD", "Varanasi");
		final String jsonData = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(student);
		System.out.println(jsonData);
	}
}