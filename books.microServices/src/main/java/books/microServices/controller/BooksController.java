package books.microServices.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import books.microServices.model.BookSpecs;

@RestController
public class BooksController {

	private Map<String, BookSpecs> books = new ConcurrentHashMap<>();
	//TODO: update the books map in default c'tor from DB.
	
	
	@RequestMapping(
			path="/books/echo",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs createBook(@RequestBody BookSpecs book) {
		
		books.put(book.getIsbn(), book);
		
		return book;
	}
	
	
	
	@RequestMapping( 
			path="/books/all",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs[] getBooksDetails(@RequestParam(name="content", required=true) String content) {
		if(content.equals("detailed")) 
			return this.books.values().toArray(new BookSpecs[0]);
			
		return null;
			
	}

}
