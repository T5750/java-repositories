package t5750.jdk11;

import java.util.Arrays;
import java.util.List;

public class Collections2Array {
	public static void main(String[] args) {
		List<String> namesList = Arrays.asList("Joe", "Julie");
		// Old way
		String[] names = namesList.toArray(new String[namesList.size()]);
		System.out.println(names.length);
		// New way
		names = namesList.toArray(String[]::new);
		System.out.println(names.length);
	}
}