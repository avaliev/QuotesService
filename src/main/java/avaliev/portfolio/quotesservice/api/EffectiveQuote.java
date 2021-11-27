package avaliev.portfolio.quotesservice.api;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class EffectiveQuote {


    String isin;

    BigDecimal elvl;
}
