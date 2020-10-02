package t5750.utils.jackson;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import t5750.utils.jackson.entity.Address;
import t5750.utils.jackson.entity.Company;
import t5750.utils.jackson.entity.Employee;
import t5750.utils.jackson.entity.Student;
import t5750.utils.util.Global;
import t5750.utils.util.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class JacksonJsonFilterTest {
	private static final Student STUDENT = new Student(Global.T5750, 30, "ABCD",
			"Varanasi");

	@Test
	public void testSerializeAllExcept() throws JsonProcessingException {
		final ObjectMapper objectMapper = JacksonUtil
				.objectMapperSerializeAllExcept("studentFilter", "stdName",
						"stdCity");
		final String jsonData = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(STUDENT);
		System.out.println(jsonData);
	}

	@Test
	public void testFilterOutAllExcept() throws JsonProcessingException {
		final ObjectMapper objectMapper = JacksonUtil
				.objectMapperFilterOutAllExcept("studentFilter", "stdName",
						"stdCity");
		final String jsonData = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(STUDENT);
		System.out.println(jsonData);
	}

	@Test
	public void testFilterOutAllExceptWithSet() throws JsonProcessingException {
		Set<String> props = new HashSet<>();
		props.add("stdName");
		props.add("stdCity");
		final ObjectMapper objectMapper = JacksonUtil
				.objectMapperFilterOutAllExcept("studentFilter", props);
		final String jsonData = objectMapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(STUDENT);
		System.out.println(jsonData);
	}

	@Test
	public void testCompanyToJSON() throws JsonProcessingException {
		final SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter("ceoFilter",
				SimpleBeanPropertyFilter.serializeAllExcept("empProfile"));
		filterProvider.addFilter("addressFilter", SimpleBeanPropertyFilter
				.filterOutAllExcept("state", "country"));
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setFilterProvider(filterProvider);
		final Employee emp = new Employee(Global.T5750, 45, "Manager");
		final Address address = new Address("Noida", "UP", "China");
		final Company company = new Company("XYZ", emp, address);
		final String jsonData = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(company);
		System.out.println(jsonData);
	}
}