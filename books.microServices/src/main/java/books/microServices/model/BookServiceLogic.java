package books.microServices.model;

import java.time.Year;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import books.microServices.dal.BookDao;
import books.microServices.dal.PublisherDao;

@Service
public class BookServiceLogic implements BookDataLogic{

	private BookDao bookDao;
	private PublisherDao publisherDao;
	
	
	@Autowired
	public BookServiceLogic(BookDao bookDao, PublisherDao publisherDao) {
		this.bookDao = bookDao;
		this.publisherDao = publisherDao;
	}
	
	@Override
	public List<String> getAllBooksIsbns(int page, int size) {
		return this.bookDao
				.findAll(PageRequest.of(page, size, Direction.ASC))
				.map(book->book.getIsbn())
				.getContent(); 
	}

	@Override
	public List<String> getAllBooksTitles(int page, int size) {
		return this.bookDao
				.findAll(PageRequest.of(page, size, Direction.ASC))
				.map(book->book.getTitle())
				.getContent();
		
	}

	@Override
	public List<BookSpecs> getAllBooks(int page, int size) {
		return this.bookDao
				.findAll(PageRequest.of(page, size, Direction.ASC, "title"))
				.getContent();
	}

	@Override
	public Optional<BookSpecs> getBookByIsbn(String isbn) {
		try{
			checkIsbnValidation(isbn);
		} catch (BookSpecIllegalException e) {
			return Optional.empty();
		}
		return this.bookDao.findById(isbn);
	}

	@Override
	public BookSpecs createBook(BookSpecs book) throws BookSpecIllegalException, BookExistException {
		validateBookSpecs(book);
		this.publisherDao.save(book.getPublisher());
		if(!this.bookDao.findById(book.getIsbn()).isPresent())
			return this.bookDao.save(book); 
		else 
			throw new BookExistException("Book with the ISBN: " + book.getIsbn() + "already exists");
		
	}

	@Override
	//doesn't delete publishers, to keep that data
	public void deleteAllBooks() {
		this.bookDao.deleteAll();
	}
	
	/************************************************************************************/
	/*								Book params validation								*/
	/************************************************************************************/
	
	private void validateBookSpecs(BookSpecs book) throws BookSpecIllegalException {
		checkIsbnValidation(book.getIsbn());
		checkTitleValidation(book.getTitle());
		checkListOfAuthorsValidation(book.getAuthors());
		
		for (String author : book.getAuthors()) 
			checkAuthorValidation(author);
		
		checkPublisherValidation(book.getPublisher());
		checkPublishedYearValidation(book.getPublishedYear());
		checkRatingValidation(book.getRating());		
	}
	
	private void checkIsbnValidation(String isbn) throws BookSpecIllegalException {
		if(isbn.length() >= 10 && isbn.length() <=13)
			return;
		else
			throw new BookSpecIllegalException("Incorrect ISBN length: " + isbn.length());
	}
	
	private void checkTitleValidation(String title) throws BookSpecIllegalException {
		if(!title.matches(".*[a-zA-Z]+.*")) 
			throw new BookSpecIllegalException("Title must contain at least 1 letter: " + title);
	}
	
	private void checkAuthorValidation(String author) throws BookSpecIllegalException {
		if(author.length() > 1) 
			if(author.matches(".*[a-zA-Z]+.*"))	//contains at least one letter
				return;
			else 
				throw new BookSpecIllegalException("Author's name should contain at least one letter: " + author);
		else 
			throw new BookSpecIllegalException("Author's name can't contain less then 2 characters: " + author);
	}
	
	private void checkListOfAuthorsValidation(List<String> authors) throws BookSpecIllegalException {
		if(authors.isEmpty())
			throw new BookSpecIllegalException("Should be at least one author of the book");
	}
	
	private void checkPublisherValidation(Publisher publisher) throws BookSpecIllegalException {
		if(!publisher.getName().matches(".*[a-zA-Z]+.*"))
			throw new BookSpecIllegalException("Publisher's name must contain at least 1 letter: " + publisher); 
	}
	
	private void checkPublishedYearValidation(int publishedYear) throws BookSpecIllegalException {
		if(publishedYear <= 0 || publishedYear > Year.now().getValue())
			throw new BookSpecIllegalException("Wrong published year value: " + publishedYear);
	}
	
	private void checkRatingValidation(int rating) throws BookSpecIllegalException {
		if(rating < 0 || rating > 5)
			throw new BookSpecIllegalException("Illegal rating value: " + rating);
	}


}
