package t5750.utils.jackson.entity;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
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
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Map<String, String> employess;

	public Company() {
	}

	public Company(String name, Employee ceo, Address address) {
		this.name = name;
		this.ceo = ceo;
		this.address = address;
	}

	public Company(String name, Map<String, String> employess) {
		this.name = name;
		this.employess = employess;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, String> getEmployess() {
		return employess;
	}

	public void setEmployess(Map<String, String> employess) {
		this.employess = employess;
	}
}