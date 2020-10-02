package t5750.utils.util;

import java.util.Set;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 */
public class JacksonUtil {
	public static ObjectMapper objectMapper(String filterId,
			SimpleBeanPropertyFilter filter) {
		final SimpleFilterProvider filterProvider = new SimpleFilterProvider();
		filterProvider.addFilter(filterId, filter);
		final ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setFilterProvider(filterProvider);
		return objectMapper;
	}

	public static ObjectMapper objectMapperSerializeAllExcept(String filterId,
			String... propertyArray) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.serializeAllExcept(propertyArray);
		return objectMapper(filterId, filter);
	}

	public static ObjectMapper objectMapperFilterOutAllExcept(String filterId,
			Set<String> properties) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept(properties);
		return objectMapper(filterId, filter);
	}

	public static ObjectMapper objectMapperFilterOutAllExcept(String filterId,
			String... propertyArray) {
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
				.filterOutAllExcept(propertyArray);
		return objectMapper(filterId, filter);
	}
}
