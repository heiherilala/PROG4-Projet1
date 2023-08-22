package com.hei.project2p1.configuration;

import com.hei.project2p1.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@PropertySource({"classpath:persistence-multiple-db.properties"})
@EnableJpaRepositories(basePackages = "com.hei.project2p1.cnaps.repository", entityManagerFactoryRef = "cnapsEntityManager", transactionManagerRef = "cnapsTransactionManager")
public class PersistenceCnapsConfiguration {
    @Autowired
    private Environment env;

    public PersistenceCnapsConfiguration() {
        super();
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean cnapsEntityManager() {
        System.out.println("loading config");
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(cnapsDataSource());
        em.setPackagesToScan("com.hei.project2p1.cnaps.entity");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", CamelCaseToSnakeCaseNamingStrategy.class.getName());
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public DataSource cnapsDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName((Objects.requireNonNull(env.getProperty("jdbc.driverClassName"))));
        dataSource.setUrl((env.getProperty("cnaps.jdbc.url")));
        dataSource.setUsername((env.getProperty("cnaps.jdbc.user")));
        dataSource.setPassword((env.getProperty("cnaps.jdbc.pass")));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager cnapsTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(cnapsEntityManager().getObject());
        return transactionManager;
    }

}
