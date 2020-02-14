package t5750.utils.entity;

import java.util.List;

import com.google.gson.annotations.Expose;

public class MovieWithNullValue {
	@Expose
	private String imdbId;
	private String director;
	@Expose
	private List actors;

	public MovieWithNullValue(String imdbId, String director, List actors) {
		this.imdbId = imdbId;
		this.director = director;
		this.actors = actors;
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
}