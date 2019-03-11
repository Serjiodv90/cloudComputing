package books.microServices.model;

import java.util.List;

public class BookSpecs {

	private String isbn;
	private String title;
	private List<String> authors;
	private String publisher;
	private int publishedYear;
	private int rating;
	
	//for spring bean
	public BookSpecs() {
		
	}
	
	
	
	public String getIsbn() {
		return isbn;
	}
	
	public void setIsbn(String isbn) throws BookSpecIllegalException {
		
		if(isbn.length() >= 10 && isbn.length() <=13)
			this.isbn = isbn;
		else
			throw new BookSpecIllegalException("Incorrect ISBN length: " + isbn.length());
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<String> authors) {
		this.authors = authors;
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	
	public int getPublishedYear() {
		return publishedYear;
	}
	
	public void setPublishedYear(int publishedYear) {
		this.publishedYear = publishedYear;
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) {
		this.rating = rating;
	}
	
	
	
}
