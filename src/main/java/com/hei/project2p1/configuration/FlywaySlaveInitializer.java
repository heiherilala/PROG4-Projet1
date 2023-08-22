package com.hei.project2p1.configuration;

import jakarta.annotation.PostConstruct;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywaySlaveInitializer {

    private final DataSource cnapsDatabase;

    @Autowired
    FlywaySlaveInitializer(@Qualifier("cnapsDataSource") DataSource cnapsDatabase){
        this.cnapsDatabase = cnapsDatabase;
    }

    @PostConstruct
    public void migrateFlyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(cnapsDatabase)
                .locations("classpath:/db/migration2")
                .load();
        flyway.migrate();

    }
}
