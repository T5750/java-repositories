package t5750.utils.jackson.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Employee {
	@JsonProperty("empName")
	private String name;
	@JsonProperty("empAge")
	private Integer age;
	@JsonProperty("empProfile")
	private String profile;

	public Employee() {
	}

	public Employee(String name, Integer age, String profile) {
		this.name = name;
		this.age = age;
		this.profile = profile;
	}
}