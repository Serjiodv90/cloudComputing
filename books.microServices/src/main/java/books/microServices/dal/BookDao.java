package books.microServices.dal;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import books.microServices.model.BookSpecs;

@RepositoryRestResource
public interface BookDao extends PagingAndSortingRepository<BookSpecs, String>{

}
