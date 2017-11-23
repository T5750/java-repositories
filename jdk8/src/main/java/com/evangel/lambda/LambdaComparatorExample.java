package com.evangel.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * This program demonstrates how to use Lambda expressions to improve code of
 * comparator used to sort list collections.
 *
 * @author www.codejava.net
 */
public class LambdaComparatorExample {
	public static void main(String[] args) {
		Book book1 = new Book("Head First Java", 38.9f);
		Book book2 = new Book("Thinking in Java", 30.0f);
		Book book3 = new Book("Effective Java", 50.0f);
		Book book4 = new Book("Code Complete", 42.5f);
		List<Book> listBooks = Arrays.asList(book1, book2, book3, book4);
		System.out.println("Before sorting:");
		System.out.println(listBooks);
		Comparator<Book> titleComparator = new Comparator<Book>() {
			public int compare(Book book1, Book book2) {
				return book1.getTitle().compareTo(book2.getTitle());
			}
		};
		Collections.sort(listBooks, titleComparator);
		System.out.println("\nAfter sorting by title:");
		System.out.println(listBooks);
		Comparator<Book> descPriceComp = (Book b1, Book b2) -> (int) (b2
				.getPrice() - b1.getPrice());
		Collections.sort(listBooks, descPriceComp);
		System.out.println("\nAfter sorting by descending price:");
		System.out.println(listBooks);
		Collections.sort(listBooks,
				(b1, b2) -> (int) (b1.getPrice() - b2.getPrice()));
		System.out.println("\nAfter sorting by ascending price:");
		System.out.println(listBooks);
	}
}

class Book {
	private String title;
	private float price;

	Book(String title, float price) {
		this.title = title;
		this.price = price;
	}

	String getTitle() {
		return this.title;
	}

	void setTitle(String title) {
		this.title = title;
	}

	float getPrice() {
		return this.price;
	}

	void setPrice(float price) {
		this.price = price;
	}

	public String toString() {
		return this.title + "-" + this.price;
	}
}