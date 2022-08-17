package dreamus.assignment.product.infrastructure.config;

import io.r2dbc.h2.H2ConnectionFactory;
import io.r2dbc.proxy.ProxyConnectionFactory;
import io.r2dbc.proxy.support.QueryExecutionInfoFormatter;
import io.r2dbc.spi.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@Configuration
@EnableR2dbcAuditing(modifyOnCreate = false)
@EnableR2dbcRepositories
@EnableTransactionManagement
public class R2dbcConfig extends AbstractR2dbcConfiguration {
    
    @Bean
    public ConnectionFactory proxyConnectionFactory() {
    
        QueryExecutionInfoFormatter formatter = QueryExecutionInfoFormatter.showAll();
        
        return ProxyConnectionFactory.builder(H2ConnectionFactory.inMemory("test"))
            .onAfterQuery(queryExecutionInfo -> log.info(formatter.format(queryExecutionInfo)))
            .build();
    }

    @NotNull
    @Override
    public ConnectionFactory connectionFactory() {
        return this.proxyConnectionFactory();
    }

    @Bean
    public ConnectionFactoryInitializer h2DbInitializer() {

        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        ResourceDatabasePopulator resourceDatabasePopulator = new ResourceDatabasePopulator();
        resourceDatabasePopulator.addScript(new ClassPathResource("schema-init.sql"));

        initializer.setConnectionFactory(this.connectionFactory());
        initializer.setDatabasePopulator(resourceDatabasePopulator);

        return initializer;
    }
}