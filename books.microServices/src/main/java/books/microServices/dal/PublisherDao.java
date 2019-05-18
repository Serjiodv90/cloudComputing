package books.microServices.dal;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import books.microServices.model.Publisher;

@RepositoryRestResource
public interface PublisherDao extends PagingAndSortingRepository<Publisher, String>{

}
