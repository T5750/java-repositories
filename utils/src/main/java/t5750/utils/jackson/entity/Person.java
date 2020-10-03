package t5750.utils.jackson.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class Person {
	private Integer id;
	private String name;
	@JsonInclude(Include.NON_NULL)
	private String city;
	@JsonInclude(Include.NON_EMPTY)
	private String country;
	private Address address;

	public Person() {
	}

	public Person(Integer id, String name, String city, String country) {
		this.id = id;
		this.name = name;
		this.city = city;
		this.country = country;
	}

	public Person(Integer id, String name, Address address) {
		this.id = id;
		this.name = name;
		this.address = address;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}
}