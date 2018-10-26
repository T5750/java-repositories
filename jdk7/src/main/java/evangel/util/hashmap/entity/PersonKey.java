package evangel.util.hashmap.entity;

/**
 * 重写的hashCode，而且刚好hashCode方法里面跟某个属性关联（这种是常用的方法，特别是关联对象的id）
 */
public class PersonKey {
	private int id = 0;
	private String name = "";
	private int height = 0;

	@Override
	public int hashCode() {
		System.out.println("person id:" + id + ",hashCode() invoked,"
				+ "hashcode:" + this.name.hashCode() + this.height);
		// 重写了原来的hashCode（）方法，把它关联到所有属性
		return id + name.hashCode() + height;
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
