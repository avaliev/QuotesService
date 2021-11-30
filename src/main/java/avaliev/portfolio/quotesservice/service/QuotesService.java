package avaliev.portfolio.quotesservice.service;

import avaliev.portfolio.quotesservice.api.EffectiveQuote;
import avaliev.portfolio.quotesservice.api.QuoteDto;
import avaliev.portfolio.quotesservice.entity.Quote;
import avaliev.portfolio.quotesservice.repos.QuoteRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuotesService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private QuoteRepository quoteRepository;

    @Autowired
    public QuotesService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    public void addQuotes(List<QuoteDto> quotes) {
        List<QuoteDto> validatedQuotes = validateQuotes(quotes);

        validatedQuotes.forEach(quoteDto -> quoteRepository.saveOrUpdateQuote(
                quoteDto.getIsin(),
                quoteDto.getBid(),
                quoteDto.getAsk()
        ));
    }

    public Optional<EffectiveQuote> getQuote(String isin) {
        Optional<Quote> quote = quoteRepository.findById(isin);
        return quote.map(EffectiveQuote::createFrom);
    }

    public List<EffectiveQuote> getAllQuotes() {
        List<EffectiveQuote> result = new ArrayList<>();
        quoteRepository.findAll().iterator().forEachRemaining(
                q -> {
                    result.add(new EffectiveQuote(q.getIsin(), q.getElvl()));
                }
        );
        return result;
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
