package t5750.utils.jackson.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Company {
	@JsonProperty("compName")
	private String name;
	@JsonFilter("ceoFilter")
	@JsonProperty("compCeo")
	private Employee ceo;
	@JsonFilter("addressFilter")
	@JsonProperty("compAddress")
	private Address address;

	public Company() {
	}

	public Company(String name, Employee ceo, Address address) {
		this.name = name;
		this.ceo = ceo;
		this.address = address;
	}
}