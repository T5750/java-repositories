package t5750.javassist.service;

import t5750.javassist.annotation.Author;

@Author(name = "T5750", year = 2020)
public class Hello {
	public void say() {
		System.out.println("Hello");
	}

	public void list(String[][] s) {
		s = new String[3][4];
	}
}