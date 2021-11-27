package avaliev.portfolio.quotesservice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("quotes")
public class Quote {

    @Id
    String isin;

    BigDecimal bid;

    BigDecimal ask;

    BigDecimal elvl;

}
