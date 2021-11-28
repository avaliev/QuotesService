package avaliev.portfolio.quotesservice.repos;


import avaliev.portfolio.quotesservice.entity.Quote;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {
}
