package evangel.util.hashmap.entity;

/**
 * 重写的equals方法是没有被调用的，我们只需要通过hashcode就可以定位相应的对象
 */
public class Person {
	private int id = 0;
	private String name = "";
	private int height = 0;

	@Override
	public int hashCode() {
		System.out.println("person id:" + id + ",hashCode() invoked,"
				+ "hashcode:" + this.name.hashCode() + this.height);
		return super.hashCode();
	}

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

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public String toString() {
		return "id:" + id + "; Name:" + this.name + "; height:" + this.height;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println("id:" + id + ", equals invokes");
		return super.equals(obj);
	}
}
