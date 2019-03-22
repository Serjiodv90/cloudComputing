package books.microServices.model;

import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
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
	
	public BookSpecs(BookSpecs book) throws BookSpecIllegalException {
		
		setIsbn(book.getIsbn());
		setTitle(book.getTitle());
		setAuthors(book.getAuthors());
		setPublisher(book.getPublisher());
		setPublishedYear(book.getPublishedYear());
		setRating(book.getRating());
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
	
	public void setTitle(String title) throws BookSpecIllegalException {
		if(title.matches(".*[a-zA-Z]+.*"))
			this.title = title;
		else 
			throw new BookSpecIllegalException("Title must contain at least 1 letter: " + title);
	}
	
	public void addAuthor(String author) throws BookSpecIllegalException {
		if(author.length() > 1) 
			if(author.matches(".*[a-zA-Z]+.*"))	//contains at least one letter
				this.authors.add(author);
			else 
				throw new BookSpecIllegalException("Author's name should contain at least one letter: " + author);
		else 
			throw new BookSpecIllegalException("Author's name can't contain less then 2 characters: " + author);
	}
	
	public List<String> getAuthors() {
		return authors;
	}
	
	public void setAuthors(List<String> authors) throws BookSpecIllegalException {
		if(authors.size() > 0) {
			this.authors = new ArrayList<>();
			for (String author : authors) 
				addAuthor(author);			
		}
			
		else 
			throw new BookSpecIllegalException("Should be at least one author of the book");
	}
	
	public String getPublisher() {
		return publisher;
	}
	
	public void setPublisher(String publisher) throws BookSpecIllegalException {
		if(publisher.matches(".*[a-zA-Z]+.*"))
			this.publisher = publisher;
		else 
			throw new BookSpecIllegalException("Publisher must contain at least 1 letter: " + publisher);
			
		
	}
	
	public int getPublishedYear() {
		return publishedYear;
	}
	
	public void setPublishedYear(int publishedYear) throws BookSpecIllegalException {
		if(publishedYear > 0 && publishedYear <= Year.now().getValue())
			this.publishedYear = publishedYear;
		else 
			throw new BookSpecIllegalException("Wrong published year value: " + publishedYear);
			
		
	}
	
	public int getRating() {
		return rating;
	}
	
	public void setRating(int rating) throws BookSpecIllegalException{
		if(rating >= 0 && rating <= 6)
			this.rating = rating;
		else 
			throw new BookSpecIllegalException("Illegal rating value: " + rating);
		
	}
	
	
	
}
