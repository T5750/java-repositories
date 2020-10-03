package t5750.utils.jackson.entity;

public class Book {
	private Integer id;
	private String name;
	private String writer;
	private String category;

	public Book() {
	}

	public Book(Integer id, String name, String writer, String category) {
		this.id = id;
		this.name = name;
		this.writer = writer;
		this.category = category;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}