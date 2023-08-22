package com.hei.project2p1.configuration;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.session.jdbc.config.annotation.SpringSessionDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableJpaRepositories(
        basePackages = "com.hei.project2p1.repository",
        entityManagerFactoryRef = "companyEntityManagerFactory",
        transactionManagerRef = "companyTransactionManager"
)
public class CompanyDataSourceConfig {

    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix="datasource.company")
    public DataSourceProperties companyDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @SpringSessionDataSource
    public DataSource companyDataSource() {
        DataSourceProperties primaryDataSourceProperties = companyDataSourceProperties();
        return DataSourceBuilder.create()
                .driverClassName(primaryDataSourceProperties.getDriverClassName())
                .url(primaryDataSourceProperties.getUrl())
                .username(primaryDataSourceProperties.getUsername())
                .password(primaryDataSourceProperties.getPassword())
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager companyTransactionManager()
    {
        EntityManagerFactory factory = companyEntityManagerFactory().getObject();
        return new JpaTransactionManager(factory);
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean companyEntityManagerFactory()
    {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(companyDataSource());
        factory.setPackagesToScan("com.hei.project2p1.model");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        jpaProperties.put("hibernate.show-sql", env.getProperty("spring.jpa.show-sql"));
        factory.setJpaProperties(jpaProperties);

        return factory;

    }

    @Bean
    public DataSourceInitializer companyDataSourceInitializer()
    {
        DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
        dataSourceInitializer.setDataSource(companyDataSource());
        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        //databasePopulator.addScript(new ClassPathResource("com.hei.project2p1.company."));
        dataSourceInitializer.setDatabasePopulator(databasePopulator);
        dataSourceInitializer.setEnabled(env.getProperty("datasource.company.initialize", Boolean.class, false));
        return dataSourceInitializer;
    }

}