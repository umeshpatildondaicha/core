package com.core.darkcoders.core.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medical Application API")
                        .version("1.0")
                        .description("API documentation for the Medical Application")
                        .contact(new Contact()
                                .name("Medical Team")
                                .email("support@medical.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://www.apache.org/licenses/LICENSE-2.0.html")))
                .schemaRequirement("bearer-key", new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT"));
    }
} 