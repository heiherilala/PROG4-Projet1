package com.hei.project2p1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Objects;

import static com.hei.project2p1.configuration.dataEntityManagerSetter.getLocalContainerEntityManagerFactoryBean;

@Configuration
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(basePackages = "com.hei.project2p1.repository", entityManagerFactoryRef = "companyEntityManager", transactionManagerRef = "companyTransactionManager")
public class PersistenceCompanyConfiguration {
    @Autowired
    private Environment env;

    public PersistenceCompanyConfiguration() {
        super();
    }

    //

    @Primary
    @Bean
    public LocalContainerEntityManagerFactoryBean companyEntityManager() {
        return getLocalContainerEntityManagerFactoryBean(companyDataSource(),"com.hei.project2p1.model",env);
    }



    @Primary
    @Bean
    public DataSource companyDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName((Objects.requireNonNull(env.getProperty("jdbc.driverClassName"))));
        dataSource.setUrl((env.getProperty("company.jdbc.url")));
        dataSource.setUsername((env.getProperty("company.jdbc.user")));
        dataSource.setPassword((env.getProperty("company.jdbc.pass")));

        return dataSource;
    }

    @Primary
    @Bean
    public PlatformTransactionManager companyTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(companyEntityManager().getObject());
        return transactionManager;
    }

}
