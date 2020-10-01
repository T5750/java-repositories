package t5750.utils.jackson.entity;

import com.fasterxml.jackson.annotation.JsonFilter;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonFilter("studentFilter")
public class Student {
	@JsonProperty("stdName")
	private String name;
	@JsonProperty("stdAge")
	private Integer age;
	@JsonProperty("stdCollege")
	private String college;
	@JsonProperty("stdCity")
	private String city;

	public Student() {
	}

	public Student(String name, Integer age, String college, String city) {
		this.name = name;
		this.age = age;
		this.college = college;
		this.city = city;
	}
}