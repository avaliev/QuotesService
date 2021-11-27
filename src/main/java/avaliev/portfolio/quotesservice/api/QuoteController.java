package avaliev.portfolio.quotesservice.api;


import avaliev.portfolio.quotesservice.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(
        value = "/quotes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
public class QuoteController {

    private QuotesService quotesService;

    @Autowired
    public QuoteController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @PutMapping(value = "")
    public void putQuotes(@RequestBody @Validated List<QuoteDto> quotes) {
        quotesService.addQuotes(quotes);
    }


    @GetMapping(value = "", params = {"isin"})
    public EffectiveQuote getQuote(@RequestParam("isin") String isin) {
        return null;
    }

    @GetMapping("all")
    public List<EffectiveQuote> getAllQuotes() {
        return null;
    }

}
