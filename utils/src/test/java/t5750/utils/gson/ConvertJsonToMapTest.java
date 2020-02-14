package t5750.utils.gson;

import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import t5750.utils.gson.entity.Employee;
import t5750.utils.gson.serialization.StringDateMapDeserializer;

/**
 * Convert JSON to a Map Using Gson
 */
public class ConvertJsonToMapTest {
	/**
	 * 2. Passing Map.class
	 */
	@Test
	public void fromJson() throws Exception {
		String jsonString = "{'employee.name':'Bob','employee.salary':10000}";
		Gson gson = new Gson();
		Map map = gson.fromJson(jsonString, Map.class);
		Assert.assertEquals(2, map.size());
		Assert.assertEquals(Double.class,
				map.get("employee.salary").getClass());
	}

	/**
	 * 3. Using TypeToken
	 */
	@Test
	public void useTypeToken() throws Exception {
		String jsonString = "{'Bob' : {'name': 'Bob Willis'},"
				+ "'Jenny' : {'name': 'Jenny McCarthy'}, "
				+ "'Steve' : {'name': 'Steven Waugh'}}";
		Gson gson = new Gson();
		Type empMapType = new TypeToken<Map<String, Employee>>() {
		}.getType();
		Map<String, Employee> nameEmployeeMap = gson.fromJson(jsonString,
				empMapType);
		Assert.assertEquals(3, nameEmployeeMap.size());
		Assert.assertEquals(Employee.class,
				nameEmployeeMap.get("Bob").getClass());
	}

	/**
	 * 4. Using Custom JsonDeserializer
	 */
	@Test
	public void useCustomJsonDeserializer() throws Exception {
		String jsonString = "{'Bob': '2017-06-01', 'Jennie':'2015-01-03'}";
		Type type = new TypeToken<Map<String, Date>>() {
		}.getType();
		Gson gson = new GsonBuilder()
				.registerTypeAdapter(type, new StringDateMapDeserializer())
				.create();
		Map<String, Date> empJoiningDateMap = gson.fromJson(jsonString, type);
		Assert.assertEquals(2, empJoiningDateMap.size());
		Assert.assertEquals(Date.class,
				empJoiningDateMap.get("Bob").getClass());
	}
}