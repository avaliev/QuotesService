package avaliev.portfolio.quotesservice.service;

import avaliev.portfolio.quotesservice.api.EffectiveQuote;
import avaliev.portfolio.quotesservice.api.QuoteDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuotesService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    public void addQuotes(List<QuoteDto> quotes) {
        List<QuoteDto> validatedQuotes = validateQuotes(quotes);
        //TODO создать запрос для вставки elvl by bid and ask за один запрос
    }

    public EffectiveQuote getQuote(String isin) {
        return null;
    }

    public List<EffectiveQuote> getAllQuotes() {
        return null;
    }


    /**
     * валидация элементов котировок
     * так как котировок много , из-за ошибки в некоторых не будем браковать весь список, это неэффективно
     *
     * @param quotes
     */
    public List<QuoteDto> validateQuotes(List<QuoteDto> quotes) {
        return quotes.parallelStream().filter(quoteDto -> {
            if (quoteDto.getIsin().length() != 12) {
                log.warn("the isin {} should contain exactly 12 chars", quoteDto.getIsin());
                return false;
            }
            if (quoteDto.getAsk().compareTo(quoteDto.getBid()) < 1) {
                log.warn("the quote for {} ask price should be greater than bid", quoteDto.getIsin());
                return false;
            }
            return true;
        }).collect(Collectors.toList());
    }

}
