package books.microServices.controller;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import javax.xml.crypto.dsig.SignedInfo;

import org.bson.internal.UnsignedLongs;
import org.springframework.beans.factory.annotation.Autowired;
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

import books.microServices.model.BookDataLogic;
import books.microServices.model.BookExistException;
import books.microServices.model.BookSpecIllegalException;
import books.microServices.model.BookSpecs;
import books.microServices.model.NoBookException;

@RestController
public class BooksController {

	private enum urlContentMappingValues {isbn, title, detailed};

	private BookDataLogic bookDataLogic;
	//	private PublisherDataLogic publisherDataLogic;

	@Autowired
	public void setBookDataLogic(BookDataLogic bookDataLogic) {
		this.bookDataLogic = bookDataLogic;
	}

	@RequestMapping(
			path="/books/echo",
			method=RequestMethod.POST,
			produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs createBook(@RequestBody BookSpecs book) throws BookSpecIllegalException, BookExistException  {
		BookSpecs tmpBook = this.bookDataLogic.createBook(book);
		return tmpBook;
	}



	private void verifyContentValue(String content) throws NoBookException {
		if(!content.equalsIgnoreCase(urlContentMappingValues.detailed.name()) && 
				!content.equalsIgnoreCase(urlContentMappingValues.isbn.name()) &&
				!content.equalsIgnoreCase(urlContentMappingValues.title.name()))
			throw new NoBookException("Couldn't find the path: /books/all?content=" + content);
	}

	@RequestMapping( 
			path="/books/all",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public Object[] getBooksDetails(@RequestParam(name="content", required=true) String content,
			@RequestParam(name="size", required=false, defaultValue="20") int size,
			@RequestParam(name="page", required=false, defaultValue="0") int page) throws NoBookException {

		if(size > 0 && page >= 0)
		{
			verifyContentValue(content);

			if(content.equalsIgnoreCase(urlContentMappingValues.detailed.name())) 
				return this.bookDataLogic.getAllBooks(page, size).toArray(new BookSpecs[0]);
			else if(content.equalsIgnoreCase(urlContentMappingValues.isbn.name()))
				return this.bookDataLogic.getAllBooksIsbns(page, size).toArray(new String[0]);
			else  {	//content = title
				return this.bookDataLogic.getAllBooksTitles(page, size).toArray(new String[0]);
			}
		}
		
		throw new NoBookException("Couldn't find the path: /books/all?content=" + content + "&size=" + size + "&page=" + page);

	}

	@RequestMapping(
			path="/books/byIsbn/{isbn}",
			method=RequestMethod.GET,
			produces=MediaType.APPLICATION_JSON_VALUE)
	public BookSpecs getByIsbn(@PathVariable("isbn") String isbn) throws NoBookException {
		return this.bookDataLogic.getBookByIsbn(isbn)
				.orElseThrow(()->new NoBookException("Couldn't find book with isbn code: " + isbn));
		
	}

	@RequestMapping(
			path="/books",
			method=RequestMethod.DELETE)
	public void deleteAll() {
		this.bookDataLogic.deleteAllBooks();
	}


	@ExceptionHandler		//set this func as the exception handler
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)	//return 400 status
	public String handleException(BookSpecIllegalException e) {
		//	e.printStackTrace();

		return new String("bad request: " + e.getMessage());
	}

	@ExceptionHandler		//set this func as the exception handler
	@ResponseStatus(value=HttpStatus.NOT_FOUND)	//return 404 status
	public String handleException(NoBookException e) {
		//	e.printStackTrace();

		return new String("error: " + e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)
	public String handleException(BookExistException e) {
		return new String(e.getMessage());
	}


}
