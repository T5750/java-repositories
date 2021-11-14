package t5750.jdk11;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class NestedBasedAccess {
	public static void main(String[] args) {
		boolean isNestMate = NestedBasedAccess.class
				.isNestmateOf(NestedBasedAccess.Inner.class);
		boolean nestHost = NestedBasedAccess.Inner.class
				.getNestHost() == NestedBasedAccess.class;
		System.out.println(isNestMate);
		System.out.println(nestHost);
		Set<String> nestedMembers = Arrays
				.stream(NestedBasedAccess.Inner.class.getNestMembers())
				.map(Class::getName).collect(Collectors.toSet());
		System.out.println(nestedMembers);
	}

	public class Inner {
	}
}