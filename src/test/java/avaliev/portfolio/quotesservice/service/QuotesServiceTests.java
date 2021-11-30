package avaliev.portfolio.quotesservice.service;

import avaliev.portfolio.quotesservice.api.EffectiveQuote;
import avaliev.portfolio.quotesservice.api.QuoteDto;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@Transactional
public class QuotesServiceTests {


    @Autowired
    QuotesService quotesService;


    @Test
    @DisplayName("Сохраняет две котировки и проверят метод getAllQuotes и проверяет установку elvl по bid")
    public void whenSaveNewQuotes_then_set_elvl_from_bid() {
        var isin = "US020123K001";
        var bid = BigDecimal.valueOf(101);
        var ask = BigDecimal.valueOf(102);
        QuoteDto firstQuote = new QuoteDto(isin, bid, ask);
        isin = "US020123K002";
        bid = BigDecimal.valueOf(2005);
        ask = BigDecimal.valueOf(2009);
        QuoteDto secondQuote = new QuoteDto(isin, bid, ask);

        quotesService.addQuotes(Lists.newArrayList(firstQuote, secondQuote));
        List<EffectiveQuote> quoteList = quotesService.getAllQuotes();

        Assertions.assertEquals(2, quoteList.size());

        for (var efq : quoteList) {
            if (efq.getIsin().equals(firstQuote.getIsin())) {
                Assertions.assertEquals(firstQuote.getBid(), efq.getElvl());
            }
            if (efq.getIsin().equals(secondQuote.getIsin())) {
                Assertions.assertEquals(secondQuote.getBid(), efq.getElvl());
            }
        }
    }




}
