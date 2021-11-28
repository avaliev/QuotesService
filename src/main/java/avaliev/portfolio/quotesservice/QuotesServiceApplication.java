package avaliev.portfolio.quotesservice;

import avaliev.portfolio.quotesservice.entity.Quote;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@EntityScan(basePackageClasses = {Quote.class})
@EnableJdbcRepositories
@SpringBootApplication
public class QuotesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuotesServiceApplication.class, args);
    }

}
