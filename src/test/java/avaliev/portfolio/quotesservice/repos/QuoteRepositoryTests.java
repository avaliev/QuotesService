package avaliev.portfolio.quotesservice.repos;


import avaliev.portfolio.quotesservice.entity.Quote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

@SpringBootTest
public class QuoteRepositoryTests {

    @Autowired
    QuoteRepository quoteRepository;

    @Test
    void save_success() {
        var isin = "US1234567890";
        var bid = BigDecimal.valueOf(101);
        var ask = BigDecimal.valueOf(102);
        Quote quote = new Quote(isin, bid, ask, bid);
        quoteRepository.save(quote);
        var savedQuote = quoteRepository.findById(isin).orElse(null);

        Assertions.assertEquals(quote, savedQuote);
    }

}
