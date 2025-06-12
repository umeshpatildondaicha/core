package com.core.darkcoders;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import lombok.extern.slf4j.Slf4j;

/**
 * Main application class.
 */
@Slf4j
@SpringBootApplication
@EnableCaching
@EnableJpaRepositories(basePackages = {"com.core.darkcoders", "com.darkcoders.platform"})
@EntityScan(basePackages = {"com.core.darkcoders", "com.darkcoders.platform"})
@ComponentScan(basePackages = {"com.core.darkcoders", "com.darkcoders.platform"})
@EnableTransactionManagement
public class CoreApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(CoreApplication.class, args);
        
        // Log all registered endpoints
        RequestMappingHandlerMapping requestMappingHandlerMapping = ctx.getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            log.info("Mapped: {} onto {}", key, value);
        });
    }
} 