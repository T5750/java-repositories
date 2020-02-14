package t5750.utils.jackson.entity;

import java.util.List;

public class MovieJackson {
	private String imdbId;
	private String director;
	private List<ActorJackson> actors;

	public MovieJackson(String imdbId, String director,
			List<ActorJackson> actors) {
		this.imdbId = imdbId;
		this.director = director;
		this.actors = actors;
	}

	public MovieJackson() {
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public String getDirector() {
		return director;
	}

	public void setDirector(String director) {
		this.director = director;
	}

	public List<ActorJackson> getActors() {
		return actors;
	}

	public void setActors(List<ActorJackson> actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Movie [imdbId=" + imdbId + ", director=" + director + ",actors="
				+ actors + "]";
	}
}