package com.github.torleifg.otlt;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

public class AbstractIntegrationTest {
    protected static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:alpine");

    static {
        postgres.start();
    }

    @DynamicPropertySource
    private static void databaseProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }
}