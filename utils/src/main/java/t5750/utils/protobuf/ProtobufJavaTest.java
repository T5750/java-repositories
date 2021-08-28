package t5750.utils.protobuf;

import t5750.utils.util.Global;

public class ProtobufJavaTest {
	public static void main(String[] args) {
		EmployeeProto.Employee.PhoneType phoneType = EmployeeProto.Employee.PhoneType.MOBILE;
		EmployeeProto.Employee.PhoneNumber phoneNumber = EmployeeProto.Employee.PhoneNumber
				.newBuilder().setNumber("1234567").setType(phoneType).build();
		EmployeeProto.Employee.AddressType addressType = EmployeeProto.Employee.AddressType.PERMANENT;
		EmployeeProto.Employee.Address address = EmployeeProto.Employee.Address
				.newBuilder().setStreet("Street").setCity("City").setZip(123456)
				.setState("State").setCountry("Country").setType(addressType)
				.build();
		EmployeeProto.Employee employee = EmployeeProto.Employee.newBuilder()
				.setId(1234).setName(Global.T5750.toUpperCase())
				.setEmail(Global.T5750 + "@email.com").addPhones(phoneNumber)
				.addAddress(address).build();
		EmployeeProto.Organization organization = EmployeeProto.Organization
				.newBuilder().addEmployee(employee).build();
		for (EmployeeProto.Employee emp : organization.getEmployeeList()) {
			System.out.println(emp.getId());
			System.out.println(emp.getName());
			System.out.println(emp.getEmail());
			System.out.println(emp.getPhonesList().get(0));
			System.out.println(emp.getAddressList().get(0));
		}
	}
}