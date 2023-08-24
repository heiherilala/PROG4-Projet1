package com.hei.project2p1.configuration;

import com.hei.project2p1.utils.hibernate.naming.CamelCaseToSnakeCaseNamingStrategy;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;

public class dataEntityManagerSetter {

    static LocalContainerEntityManagerFactoryBean getLocalContainerEntityManagerFactoryBean(DataSource dataSource, String packageToScan, Environment env) {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);
        em.setPackagesToScan(packageToScan);
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        final HashMap<String, Object> properties = new HashMap<String, Object>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        properties.put("hibernate.physical_naming_strategy", CamelCaseToSnakeCaseNamingStrategy.class.getName());
        em.setJpaPropertyMap(properties);
        return em;
    }
}
