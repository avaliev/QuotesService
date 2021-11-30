package avaliev.portfolio.quotesservice.api;

import avaliev.portfolio.quotesservice.entity.Quote;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class EffectiveQuote {

    private String isin;

    private BigDecimal elvl;

    public static EffectiveQuote createFrom(Quote q) {
        if (q == null) return null;
        return new EffectiveQuote(q.getIsin(), q.getElvl());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EffectiveQuote that = (EffectiveQuote) o;
        return isin.equals(that.isin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(isin);
    }
}
