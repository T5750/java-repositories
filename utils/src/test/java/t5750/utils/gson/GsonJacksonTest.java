package t5750.utils.gson;

import java.text.SimpleDateFormat;
import java.util.Arrays;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import t5750.utils.gson.entity.ActorGson;
import t5750.utils.entity.Movie;
import t5750.utils.entity.MovieWithNullValue;
import t5750.utils.gson.serialization.ActorGsonDeserializer;
import t5750.utils.gson.serialization.ActorGsonSerializer;

/**
 * Gson vs Jackson
 */
public class GsonJacksonTest {
	private static final String JSON_INPUT = "{\"imdbId\":\"tt0472043\",\"actors\":"
			+ "[{\"imdbId\":\"nm2199632\",\"dateOfBirth\":\"1982-09-21T12:00:00+01:00\","
			+ "\"filmography\":[\"Apocalypto\",\"Beatdown\",\"Wind Walkers\"]}]}";

	/**
	 * 3.1. Simple Serialization
	 */
	@Test
	public void simpleSerialization() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ActorGson rudyYoungblood = new ActorGson("nm2199632",
				sdf.parse("21-09-1982"),
				Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
		Movie movie = new Movie("tt0472043", "Mel Gibson",
				Arrays.asList(rudyYoungblood));
		String serializedMovie = new Gson().toJson(movie);
		System.out.println(serializedMovie);
	}

	/**
	 * 3.2. Custom Serialization
	 */
	@Test
	public void customSerialization() throws Exception {
		Gson gson = new GsonBuilder().setPrettyPrinting()
				.excludeFieldsWithoutExposeAnnotation().serializeNulls()
				.disableHtmlEscaping()
				.registerTypeAdapter(ActorGson.class, new ActorGsonSerializer())
				.create();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		ActorGson rudyYoungblood = new ActorGson("nm2199632",
				sdf.parse("21-09-1982"),
				Arrays.asList("Apocalypto", "Beatdown", "Wind Walkers"));
		MovieWithNullValue movieWithNullValue = new MovieWithNullValue(null,
				"Mel Gibson", Arrays.asList(rudyYoungblood));
		String serializedMovie = gson.toJson(movieWithNullValue);
		System.out.println(serializedMovie);
	}

	/**
	 * 4.1. Simple Deserialization
	 */
	@Test
	public void simpleDeserialization() throws Exception {
		Movie outputMovie = new Gson().fromJson(JSON_INPUT, Movie.class);
		System.out.println(outputMovie.toString());
	}

	/**
	 * 4.2. Custom Deserialization
	 */
	@Test
	public void customDeserialization() throws Exception {
		Gson gson = new GsonBuilder().registerTypeAdapter(ActorGson.class,
				new ActorGsonDeserializer()).create();
		Movie outputMovie = gson.fromJson(JSON_INPUT, Movie.class);
		System.out.println(outputMovie.toString());
	}
}