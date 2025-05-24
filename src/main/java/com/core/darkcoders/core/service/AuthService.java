package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.LoginRequest;
import com.core.darkcoders.core.dto.LoginResponse;
import com.core.darkcoders.core.dto.RefreshTokenRequest;
import com.core.darkcoders.core.dto.RegistrationResponse;
import com.core.darkcoders.core.exception.AuthenticationException;
import com.core.darkcoders.core.model.User;
import com.core.darkcoders.core.repository.UserRepository;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final RestTemplate restTemplate;
    private final Keycloak keycloak;

    @Value("${keycloak.auth-server-url}")
    private String keycloakUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    public RegistrationResponse registerUser(String email, String password, String role) {
        try {
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            // Create user
            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(email);
            user.setEmail(email);
            user.setEmailVerified(true);

            // Create credentials
            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(password);
            credentials.setTemporary(false);
            user.setCredentials(Collections.singletonList(credentials));

            // Create user in Keycloak
            Response response = usersResource.create(user);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new AuthenticationException("Failed to create user in Keycloak");
            }
            
            String userId = response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf("/") + 1);

            // Assign role
            realmResource.users().get(userId).roles().realmLevel()
                    .add(Collections.singletonList(realmResource.roles().get(role).toRepresentation()));

            return new RegistrationResponse(userId, "User registered successfully");
        } catch (Exception e) {
            log.error("Error registering user: {}", e.getMessage());
            throw new AuthenticationException("Failed to register user: " + e.getMessage());
        }
    }

    public LoginResponse login(LoginRequest loginRequest) {
        try {
            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakUrl, realm);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "password");
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("username", loginRequest.getEmail());
            formData.add("password", loginRequest.getPassword());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
            
            Map<String, Object> tokenResponse = restTemplate.postForObject(tokenUrl, request, Map.class);
            
            if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
                throw new AuthenticationException("Invalid credentials");
            }

            return new LoginResponse(
                (String) tokenResponse.get("access_token"),
                (String) tokenResponse.get("refresh_token"),
                (Integer) tokenResponse.get("expires_in"),
                (String) tokenResponse.get("token_type")
            );
        } catch (Exception e) {
            log.error("Error during login: {}", e.getMessage());
            throw new AuthenticationException("Login failed: " + e.getMessage());
        }
    }

    public LoginResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        try {
            String tokenUrl = String.format("%s/realms/%s/protocol/openid-connect/token", keycloakUrl, realm);
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
            formData.add("grant_type", "refresh_token");
            formData.add("client_id", clientId);
            formData.add("client_secret", clientSecret);
            formData.add("refresh_token", refreshTokenRequest.getRefreshToken());

            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(formData, headers);
            
            Map<String, Object> tokenResponse = restTemplate.postForObject(tokenUrl, request, Map.class);
            
            if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
                throw new AuthenticationException("Invalid refresh token");
            }

            return new LoginResponse(
                (String) tokenResponse.get("access_token"),
                (String) tokenResponse.get("refresh_token"),
                (Integer) tokenResponse.get("expires_in"),
                (String) tokenResponse.get("token_type")
            );
        } catch (Exception e) {
            log.error("Error refreshing token: {}", e.getMessage());
            throw new AuthenticationException("Token refresh failed: " + e.getMessage());
        }
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("User not found"));
    }
} 