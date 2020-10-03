package t5750.utils.jackson.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Writer {
	@JsonProperty("writerName")
	private String name;
	@JsonFormat(shape = JsonFormat.Shape.NUMBER)
	@JsonProperty("bdate")
	private Date birthDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MMM-dd HH:mm:ss z", timezone = "GMT+8")
	@JsonProperty("pubDate")
	private Date recentBookPubDate;

	public Writer() {
	}

	public Writer(String name, Date birthDate, Date recentBookPubDate) {
		this.name = name;
		this.birthDate = birthDate;
		this.recentBookPubDate = recentBookPubDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public Date getRecentBookPubDate() {
		return recentBookPubDate;
	}

	public void setRecentBookPubDate(Date recentBookPubDate) {
		this.recentBookPubDate = recentBookPubDate;
	}
}