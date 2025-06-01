package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.LoginRequest;
import com.core.darkcoders.core.dto.LoginResponse;
import com.core.darkcoders.core.dto.RefreshTokenRequest;
import com.core.darkcoders.core.dto.RegistrationResponse;
import com.core.darkcoders.core.dto.OTPRegistrationRequest;
import com.core.darkcoders.core.exception.AuthenticationException;
import com.core.darkcoders.core.model.User;
import com.core.darkcoders.core.model.UserRole;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

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

    public LoginResponse loginWithOTP(String mobileNumber) {
        try {
            // Find user by mobile number
            Optional<User> userOpt = userRepository.findByMobileNumber(mobileNumber);
            if (userOpt.isEmpty()) {
                throw new AuthenticationException("User not found with mobile number: " + mobileNumber);
            }

            User user = userOpt.get();
            
            // For patients, we'll generate a simple token without Keycloak
            if (user.getRole() == UserRole.ROLE_PATIENT) {
                // Generate a simple token (in production, use proper JWT)
                String token = "PATIENT_" + UUID.randomUUID().toString();
                return new LoginResponse(
                    token,
                    null, // no refresh token for simple auth
                    3600, // 1 hour
                    "Bearer"
                );
            }
            
            // For doctors, continue with Keycloak authentication
            try {
                String tokenUrl = keycloakUrl + "/realms/" + realm + "/protocol/openid-connect/token";
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

                MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
                map.add("grant_type", "password");
                map.add("client_id", clientId);
                map.add("client_secret", clientSecret);
                map.add("username", user.getEmail());
                map.add("password", user.getPassword());

                HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
                ResponseEntity<Map> response = restTemplate.postForEntity(tokenUrl, request, Map.class);

                if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                    Map<String, Object> tokenResponse = response.getBody();
                    return new LoginResponse(
                        (String) tokenResponse.get("access_token"),
                        (String) tokenResponse.get("refresh_token"),
                        (Integer) tokenResponse.get("expires_in"),
                        (String) tokenResponse.get("token_type")
                    );
                }
            } catch (Exception e) {
                log.error("Error getting token from Keycloak: {}", e.getMessage());
                throw new AuthenticationException("Failed to authenticate with Keycloak");
            }

            throw new AuthenticationException("Invalid user role");
        } catch (Exception e) {
            log.error("Login failed: {}", e.getMessage());
            throw new AuthenticationException("Login failed: " + e.getMessage());
        }
    }

    public RegistrationResponse registerUserWithOTP(OTPRegistrationRequest request) {
        try {
            // Check if user already exists
            if (userRepository.existsByEmail(request.getEmail())) {
                throw new AuthenticationException("User with email " + request.getEmail() + " already exists");
            }
            if (userRepository.existsByMobileNumber(request.getMobileNumber())) {
                throw new AuthenticationException("User with mobile number " + request.getMobileNumber() + " already exists");
            }

            // Create user in Keycloak
            RealmResource realmResource = keycloak.realm(realm);
            UsersResource usersResource = realmResource.users();

            UserRepresentation user = new UserRepresentation();
            user.setEnabled(true);
            user.setUsername(request.getEmail());
            user.setEmail(request.getEmail());
            user.setEmailVerified(true);

            // Create credentials with a random password since we're using OTP
            String randomPassword = java.util.UUID.randomUUID().toString();
            CredentialRepresentation credentials = new CredentialRepresentation();
            credentials.setType(CredentialRepresentation.PASSWORD);
            credentials.setValue(randomPassword);
            credentials.setTemporary(false);
            user.setCredentials(Collections.singletonList(credentials));

            Response response = usersResource.create(user);
            if (response.getStatus() != Response.Status.CREATED.getStatusCode()) {
                throw new AuthenticationException("Failed to create user in Keycloak");
            }
            
            String userId = response.getLocation().getPath().substring(response.getLocation().getPath().lastIndexOf("/") + 1);

            // Assign role
            realmResource.users().get(userId).roles().realmLevel()
                    .add(Collections.singletonList(realmResource.roles().get(request.getRole()).toRepresentation()));

            // Create user in our database
            User newUser = new User();
            newUser.setUsername(request.getEmail());
            newUser.setEmail(request.getEmail());
            newUser.setPassword(randomPassword); // Store the random password
            newUser.setMobileNumber(request.getMobileNumber());
            newUser.setKeycloakId(userId);
            newUser.setFirstName(request.getFirstName());
            newUser.setLastName(request.getLastName());
            newUser.setRole(UserRole.valueOf(request.getRole()));
            userRepository.save(newUser);

            return new RegistrationResponse(userId, "User registered successfully");
        } catch (Exception e) {
            log.error("Error registering user with OTP: {}", e.getMessage());
            throw new AuthenticationException("Failed to register user: " + e.getMessage());
        }
    }
} 