package avaliev.portfolio.quotesservice.api;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


@AllArgsConstructor
@Data
public class QuoteDto {

    @NotBlank
    @Size(min = 12, max = 12)
    private String isin;

    @DecimalMin("0.01")
    @NotNull
    private BigDecimal bid;

    @DecimalMin("0.01")
    @NotNull
    private BigDecimal ask;

}
