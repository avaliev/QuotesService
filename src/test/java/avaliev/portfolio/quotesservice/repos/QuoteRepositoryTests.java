package avaliev.portfolio.quotesservice.repos;


import avaliev.portfolio.quotesservice.entity.Quote;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Transactional
@SpringBootTest
public class QuoteRepositoryTests {

    @Autowired
    QuoteRepository quoteRepository;

    @Test
    void save_success() {
        var isin = "US1234567892";
        var bid = BigDecimal.valueOf(101);
        var ask = BigDecimal.valueOf(102);
        Quote quote = new Quote(isin, bid, ask, bid);
        quoteRepository.save(quote);
        var savedQuote = quoteRepository.findById(isin).orElse(null);

        Assertions.assertEquals(quote, savedQuote);
    }


    @Test
    @DisplayName("сохраняет ту же котировку повторно и проверяет elvl")
    void saves_same_quote_and_check_elvl() {
        var isin = "RU0009029540";
        var bid1 = BigDecimal.valueOf(252);
        var ask1 = BigDecimal.valueOf(255);

        quoteRepository.saveOrUpdateQuote(isin, bid1, ask1);
        var quote1 = quoteRepository.findById(isin).get();
        Assertions.assertEquals(quote1.getElvl(), bid1);

        var bid2 = BigDecimal.valueOf(255);
        var ask2 = BigDecimal.valueOf(259);

        quoteRepository.saveOrUpdateQuote(isin, bid2, ask2);
        var quote2 = quoteRepository.findById(isin).get();
        Assertions.assertEquals(quote2.getElvl(), bid2);

        var bid3 = BigDecimal.valueOf(250);
        var ask3 = BigDecimal.valueOf(253);

        quoteRepository.saveOrUpdateQuote(isin, bid3, ask3);
        var quote3 = quoteRepository.findById(isin).get();
        Assertions.assertEquals(quote3.getElvl(), ask3);

    }

    @Test
    @DisplayName("сохраняет котировку без bid, но с ask")
    void setNewQuoteWithoutBid() {
        var isin = "RU0009029545";
        var bid = BigDecimal.valueOf(252);
        var ask = BigDecimal.valueOf(255);
        quoteRepository.saveOrUpdateQuote(isin, null, ask);
        var quote = quoteRepository.findById(isin).get();

        Assertions.assertEquals(ask, quote.getElvl());
    }

}
