package t5750.utils.jackson.entity;

import java.util.Date;
import java.util.List;

public class ActorJackson {
	private String imdbId;
	private Date dateOfBirth;
	private List<String> filmography;

	public ActorJackson(String imdbId, Date dateOfBirth,
			List<String> filmography) {
		this.imdbId = imdbId;
		this.dateOfBirth = dateOfBirth;
		this.filmography = filmography;
	}

	public String getImdbId() {
		return imdbId;
	}

	public void setImdbId(String imdbId) {
		this.imdbId = imdbId;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<String> getFilmography() {
		return filmography;
	}

	public void setFilmography(List<String> filmography) {
		this.filmography = filmography;
	}
}