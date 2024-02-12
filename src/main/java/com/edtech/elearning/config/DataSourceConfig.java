package com.edtech.elearning.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "primaryDS")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.elearning")
    public DataSource getDataSource() {
        return DataSourceBuilder.create().build();
    }

}
