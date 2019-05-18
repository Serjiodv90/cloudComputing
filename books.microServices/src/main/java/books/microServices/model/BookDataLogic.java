package books.microServices.model;

import java.util.List;
import java.util.Optional;

public interface BookDataLogic {
	
	public List<String> getAllBooksIsbns(int page, int size);
	
	public List<String> getAllBooksTitles(int page, int size);
	
	public List<BookSpecs> getAllBooks(int page, int size);
	
	public Optional<BookSpecs> getBookByIsbn(String isbn);
	
	public BookSpecs createBook(BookSpecs book) throws BookSpecIllegalException, BookExistException;
	
	public void deleteAllBooks();

	
}
