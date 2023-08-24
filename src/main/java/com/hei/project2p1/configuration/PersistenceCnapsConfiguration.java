package com.hei.project2p1.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
@EnableJpaRepositories(basePackages = "com.hei.project2p1.repository.cnaps", entityManagerFactoryRef = "cnapsEntityManager", transactionManagerRef = "cnapsTransactionManager")
public class PersistenceCnapsConfiguration {
    @Autowired
    private Environment env;

    public PersistenceCnapsConfiguration() {
        super();
    }

    //

    @Bean
    public LocalContainerEntityManagerFactoryBean cnapsEntityManager() {
        return getLocalContainerEntityManagerFactoryBean(cnapsDataSource(),"com.hei.project2p1.repository.cnaps.entity",env);
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
