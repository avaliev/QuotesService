package avaliev.portfolio.quotesservice.api;


import avaliev.portfolio.quotesservice.service.QuotesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping(
        value = "/quotes",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
@Validated
public class QuoteController {

    private QuotesService quotesService;

    @Autowired
    public QuoteController(QuotesService quotesService) {
        this.quotesService = quotesService;
    }

    @PutMapping(value = "")
    public void putQuotes(@RequestBody @Valid QuoteDto... quotes) {
        quotesService.addQuotes(Arrays.asList(quotes));
    }


    @GetMapping(value = "", params = {"isin"})
    public EffectiveQuote getQuote(@RequestParam("isin") String isin) {
        Optional<EffectiveQuote> quoteOptional = quotesService.getQuote(isin);
        if (quoteOptional.isPresent()) {
            return quoteOptional.get();
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, String.format("Quote for %s not found", isin));
        }
    }

    @GetMapping("all")
    public List<EffectiveQuote> getAllQuotes() {
        return quotesService.getAllQuotes();
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity handleException(ConstraintViolationException constraintViolationException) {
        Set<ConstraintViolation<?>> violations = constraintViolationException.getConstraintViolations();
        String errorMessage = "";
        if (!violations.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            violations.forEach(violation -> builder.append(" " + violation.getMessage()));
            errorMessage = builder.toString();
        } else {
            errorMessage = "ConstraintViolationException occured.";
        }
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

}
