package t5750.utils.jackson.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Address {
	@JsonProperty("city")
	private String city;
	@JsonProperty("state")
	private String state;
	@JsonProperty("country")
	private String country;

	public Address() {
	}

	public Address(String city, String state, String country) {
		this.city = city;
		this.state = state;
		this.country = country;
	}
}