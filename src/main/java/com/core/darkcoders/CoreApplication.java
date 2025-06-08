package com.core.darkcoders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main application class.
 */
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = "com.core.darkcoders")
@EntityScan(basePackages = "com.core.darkcoders")
@ComponentScan(basePackages = "com.core.darkcoders")
@EnableTransactionManagement
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
} 