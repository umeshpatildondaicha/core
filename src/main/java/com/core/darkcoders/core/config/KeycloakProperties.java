package com.core.darkcoders.core.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {
    private String authServerUrl;
    private String realm;
    private String resource;
    private String publicClient;
    private String principalAttribute;
    private Credentials credentials;
    private Admin admin;

    @Data
    public static class Credentials {
        private String secret;
    }

    @Data
    public static class Admin {
        private String username;
        private String password;
        private String clientId;
    }
} 