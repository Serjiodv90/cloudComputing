package books.microServices.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import books.microServices.model.BookSpecIllegalException;
import books.microServices.model.BookSpecs;

@RestController
public class BooksController {

	private enum urlContentMappingValues {isbn, title, detailed};

	private Map<String, BookSpecs> books = new ConcurrentHashMap<>();

	@RequestMapping(
			path="/books/echo",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs createBook(@RequestBody BookSpecs book) throws BookSpecIllegalException  {
		
		books.put(book.getIsbn(), book);
		return book;
	}
	
	private void verifyContentValue(String content) throws BookSpecIllegalException {
		if(!content.equalsIgnoreCase(urlContentMappingValues.detailed.name()) && 
		   !content.equalsIgnoreCase(urlContentMappingValues.isbn.name()) &&
		   !content.equalsIgnoreCase(urlContentMappingValues.title.name()))
			throw new BookSpecIllegalException("Couldn't find the path: /books/all?content=" + content);
	}

	@RequestMapping( 
			path="/books/all",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Object[] getBooksDetails(@RequestParam(name="content", required=true) String content) throws BookSpecIllegalException {
		
		
		verifyContentValue(content);
		
		if(!this.books.isEmpty()) {
			if(content.equalsIgnoreCase(urlContentMappingValues.detailed.name())) 
				return this.books.values().toArray(new BookSpecs[0]);
			else if(content.equalsIgnoreCase(urlContentMappingValues.isbn.name()))
				return this.books.keySet().toArray(new String[0]);
			else  {	//content = title
				return this.books.values().stream()
						.map(book->book.getTitle())
						.collect(Collectors.toList())
						.toArray(new String[0]);
			}
		}
		else 
			throw new BookSpecIllegalException("Couldn't find books");
		

	}

	@RequestMapping(
			path="/books/byIsbn/{isbn}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs getByIsbn(@PathVariable("isbn") String isbn) throws BookSpecIllegalException {

		if (books.containsKey(isbn)) {
			return books.get(isbn);
		}
		else {
			throw new BookSpecIllegalException("Couldn't find book with isbn code: " + isbn);
		}
	}

	@RequestMapping(
			path="/books",
			method=RequestMethod.DELETE)
	public void deleteAll() {
		books.clear();
	}


	@ExceptionHandler		//set this func as the exception handler
	@ResponseStatus(value=HttpStatus.NOT_FOUND)	//return 404 status
	public String handleException(BookSpecIllegalException e) {
		//	e.printStackTrace();

		return new String("error: " + e.getMessage());
	}




}
