package t5750.utils.protobuf;

import t5750.utils.util.Global;

public class AddressBookTest {
	public static void main(String[] args) {
		Person person = Person.newBuilder().setId(1234)
				.setName(Global.T5750.toUpperCase())
				.setEmail(Global.T5750 + "@example.com")
				.addPhones(Person.PhoneNumber.newBuilder().setNumber("555-4321")
						.setType(Person.PhoneType.HOME))
				.build();
		System.out.println(person.getId());
		System.out.println(person.getName());
		System.out.println(person.getEmail());
		System.out.println(person.getPhonesList().get(0));
	}
}