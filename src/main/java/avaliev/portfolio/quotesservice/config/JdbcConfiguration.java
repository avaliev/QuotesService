package avaliev.portfolio.quotesservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.core.mapping.JdbcMappingContext;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.data.relational.core.mapping.NamingStrategy;

import java.util.Optional;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    @Bean
    @Override
    public JdbcMappingContext jdbcMappingContext(Optional<NamingStrategy> namingStrategy,
                                                 JdbcCustomConversions customConversions) {
        JdbcMappingContext mappingContext = super.jdbcMappingContext(namingStrategy, customConversions);
        // problem with quotes in H2
        mappingContext.setForceQuote(false);
        return mappingContext;
    }
}
