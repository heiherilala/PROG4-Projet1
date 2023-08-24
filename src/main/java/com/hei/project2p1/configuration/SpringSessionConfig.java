package com.hei.project2p1.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;
import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

@Configuration
@EnableJdbcHttpSession
public class SpringSessionConfig extends AbstractHttpSessionApplicationInitializer {

    /*
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    @Primary
    public TransactionAwareDataSourceProxy transactionAwareDataSource(@Qualifier("companyDataSource") DataSource dataSource) {
        return new TransactionAwareDataSourceProxy(dataSource);
    }



    @Bean
    public PlatformTransactionManager springSessionTransactionManager(@Qualifier("companyDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
    */


}
