package t5750.utils.entity;

import java.util.List;

public class Movie {
	private String imdbId;
	private String director;
	private List actors;

	public Movie(String imdbId, String director, List actors) {
		this.imdbId = imdbId;
		this.director = director;
		this.actors = actors;
	}

	public Movie() {
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

	public List getActors() {
		return actors;
	}

	public void setActors(List actors) {
		this.actors = actors;
	}

	@Override
	public String toString() {
		return "Movie [imdbId=" + imdbId + ", director=" + director + ",actors="
				+ actors + "]";
	}
}