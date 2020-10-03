package t5750.utils.jackson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import t5750.utils.jackson.entity.Book;
import t5750.utils.jackson.entity.Company;
import t5750.utils.jackson.entity.Person;
import t5750.utils.util.Global;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonIgnoreNullTest {
	@Test
	public void ignoreNullEmptyAtPropertyLevel()
			throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		final Person person = new Person(110, Global.T5750, null, "");
		String jsonPerson = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(person);
		System.out.println(jsonPerson);
	}

	@Test
	public void ignoreNullEmptyWithObjectMapper()
			throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		final Book book = new Book(101, "Spring", null, "");
		String jsonBook = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(book);
		System.out.println(jsonBook);
	}

	@Test
	public void ignoreNullEmptyMap() throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		List<Company> list = new ArrayList<>();
		Map<String, String> map = new HashMap<>();
		map.put("A11", Global.T5750);
		Company company1 = new Company("AAAA", map);
		list.add(company1);
		Company company2 = new Company("BBBB", new HashMap<>());
		list.add(company2);
		Company company3 = new Company("CCCC", null);
		list.add(company3);
		String jsonCompany = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(list);
		System.out.println(jsonCompany);
	}

	@Test
	public void ignoreNullEmptyMapWithObjectMapper()
			throws JsonProcessingException {
		final ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
		Map<String, String> map = new HashMap<>();
		map.put("A11", Global.T5750);
		map.put("A22", null);
		map.put("A33", "");
		String jsonMap = mapper.writerWithDefaultPrettyPrinter()
				.writeValueAsString(map);
		System.out.println(jsonMap);
	}
}
