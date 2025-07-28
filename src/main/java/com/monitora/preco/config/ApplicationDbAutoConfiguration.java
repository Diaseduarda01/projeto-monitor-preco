package com.monitora.preco.config.database;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.jdbc.JdbcConnectionDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@AutoConfiguration
@RequiredArgsConstructor
@ConditionalOnMissingBean(name = "applicationDataSource")
public class ApplicationDbAutoConfiguration {

    @Qualifier("jdbcConnectionDetails")
    private final JdbcConnectionDetails jdbcConnectionDetails;

    @Primary
    @Bean("applicationDataSource")
    public DataSource applicationDataSource() {
        var props = new DataSourceProperties();
        props.setUrl(jdbcConnectionDetails.getJdbcUrl());
        props.setUsername(jdbcConnectionDetails.getUsername());
        props.setPassword(jdbcConnectionDetails.getPassword());
        return props.initializeDataSourceBuilder().build();
    }
}