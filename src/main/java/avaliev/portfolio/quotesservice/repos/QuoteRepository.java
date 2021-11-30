package avaliev.portfolio.quotesservice.repos;


import avaliev.portfolio.quotesservice.entity.Quote;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface QuoteRepository extends CrudRepository<Quote, String> {

    /**
     * для скорости надежности логика вставка и обновления делается
     * на стороне БД посредством 'upsert' операции
     *
     * @param isin
     * @param bid
     * @param ask
     * @param elvl
     */
    @Modifying
    @Query("insert into quotes (isin, bid, ask, elvl) " +
            "values (:isin, :bid, :ask, :elvl) " +
            " ON CONFLICT (isin) " +
            " DO UPDATE SET " +
            " bid = excluded.bid, " +
            " ask = excluded.ask, " +
            " elvl = (case " +
            " when excluded.bid is null then excluded.ask " +
            " when quotes.elvl < excluded.bid then excluded.bid " +
            " when quotes.elvl > excluded.ask then excluded.ask " +
            " else excluded.bid end)")
    void _insertOrUpdateQuote(
            @Param("isin") String isin,
            @Param("bid") BigDecimal bid,
            @Param("ask") BigDecimal ask,
            @Param("elvl") BigDecimal elvl);

    default void saveOrUpdateQuote(String isin, BigDecimal bid, BigDecimal ask) {
        _insertOrUpdateQuote(isin, bid, ask, bid != null ? bid : ask);
    }

}
