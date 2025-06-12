package com.core.darkcoders.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
    "com.core.darkcoders.core.dao",
    "com.darkcoders.platform.configuration.multilingual.dao"
})
public class DaoConfig {
} 