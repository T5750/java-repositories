package t5750.utils.jackson;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import t5750.utils.entity.Movie;
import t5750.utils.entity.MovieWithNullValue;
import t5750.utils.jackson.entity.ActorJackson;
import t5750.utils.jackson.serialization.ActorJacksonSerializer;

/**
 * Gson vs Jackson
 */
public class JacksonGsonTest {
	private static final String JSON_INPUT = "{\"imdbId\":\"tt0472043\",\"actors\":"
			+ "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\","
			+ "\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

	/**
	 * 6.1. Simple Serialization
	 */
	@Test
	public void simpleSerialization() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ActorJackson rudyYoungblood = new ActorJackson("nm2199632",
				sdf.parse("21-09-1982"),
				Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
		Movie movie = new Movie("tt0472043", "Mel Gibson",
				Arrays.asList(rudyYoungblood));
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = mapper.writeValueAsString(movie);
		System.out.println(jsonResult);
	}

	/**
	 * 6.2. Custom Serialization
	 */
	@Test
	public void customSerialization() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ActorJackson rudyYoungblood = new ActorJackson("nm2199632",
				sdf.parse("21-09-1982"),
				Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
		MovieWithNullValue movieWithNullValue = new MovieWithNullValue(null,
				"Mel Gibson", Arrays.asList(rudyYoungblood));
		SimpleModule module = new SimpleModule();
		module.addSerializer(new ActorJacksonSerializer(ActorJackson.class));
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = mapper.registerModule(module)
				.writer(new DefaultPrettyPrinter())
				.writeValueAsString(movieWithNullValue);
		System.out.println(jsonResult);
	}

	/**
	 * 7.1. Simple Deserialization
	 */
	@Test
	public void simpleDeserialization() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Movie movie = mapper.readValue(JSON_INPUT, Movie.class);
		System.out.println(movie.toString());
	}

	/**
	 * 7.2. Custom Deserialization
	 */
	@Test
	public void customDeserialization() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
		mapper.setDateFormat(df);
		Movie movie = mapper.readValue(JSON_INPUT, Movie.class);
		System.out.println(movie.toString());
	}
}