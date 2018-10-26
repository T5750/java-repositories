package evangel.util.hashmap.entity;

public class Dog {
	private int id = 0;
	private String name = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		System.out.println("dog's hashCode() invoked");
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("dog's equals invokes");
		return super.equals(obj);
	}
}
