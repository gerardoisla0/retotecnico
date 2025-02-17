package com.seek.retotecnico.mysql.config;

import io.r2dbc.pool.ConnectionPool;
import io.r2dbc.pool.ConnectionPoolConfiguration;
import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class MySQLConnectionPool {

    @Bean
    public ConnectionFactory connectionFactory(MySQLConnectionProperties properties) {
        ConnectionFactory connectionFactory = ConnectionFactories.get(properties.getUrl());

        ConnectionPoolConfiguration poolConfiguration = ConnectionPoolConfiguration.builder(connectionFactory)
                .name("mysql-connection-pool")
                .initialSize(10)
                .maxSize(50)
                .maxIdleTime(Duration.ofMinutes(5))
                .maxLifeTime(Duration.ofSeconds(30))
                .maxAcquireTime(Duration.ofSeconds(10))
                .build();

        return new ConnectionPool(poolConfiguration);
    }

}

