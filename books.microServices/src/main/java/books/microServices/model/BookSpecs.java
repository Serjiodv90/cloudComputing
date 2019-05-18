package books.microServices.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="BOOKS")
public class BookSpecs {

	private String isbn;
	private String title;
	private List<String> authors;
	private int publishedYear;
	private int rating;
	
	@org.springframework.data.mongodb.core.mapping.DBRef(lazy=false)
	private Publisher publisher;

	//for spring bean
	public BookSpecs() {
		this.authors = new ArrayList<>();
	}

	@Id
	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;		
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void addAuthor(String author) {
		this.authors.add(author);

	}

	public List<String> getAuthors() {
		return authors;
	}

	public void setAuthors(List<String> authors) {
		if(authors.size() > 0) {
			for (String author : authors) 
				addAuthor(author);	
		}
		else 
			this.authors = authors;
	}

	@Reference
	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
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

	@Override
	public String toString() {
		return "BookSpecs [isbn=" + isbn + ", title=" + title + ", authors=" + authors + ", publishedYear="
				+ publishedYear + ", rating=" + rating + ", publisher=" + publisher + "]";
	}


}
