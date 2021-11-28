package avaliev.portfolio.quotesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
@Table("quotes")
public class Quote implements Persistable<String> {

    @Id
    String isin;

    BigDecimal bid;

    BigDecimal ask;

    BigDecimal elvl;

    @Override
    public String getId() {
        return isin;
    }

    @Override
    public boolean isNew() {
        return true;
    }
}
