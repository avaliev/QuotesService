package avaliev.portfolio.quotesservice.api;

import lombok.Data;

import javax.validation.constraints.Size;
import java.math.BigDecimal;


@Data
public class QuoteDto {

    @Size(min = 12, max = 12)
    private String isin;

    private BigDecimal bid;

    private BigDecimal ask;
}
