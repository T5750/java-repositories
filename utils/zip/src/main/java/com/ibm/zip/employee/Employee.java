package com.ibm.zip.employee;

import java.io.Serializable;

/**
 * 清单 6. 源代码
 */
public class Employee implements Serializable {
	String name;
	int age;
	int salary;

	public Employee(String name, int age, int salary) {
		this.name = name;
		this.age = age;
		this.salary = salary;
	}

	public void print() {
		System.out.println("Record for: " + name);
		System.out.println("Name: " + name);
		System.out.println("Age: " + age);
		System.out.println("Salary: " + salary);
	}
}