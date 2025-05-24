package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.AccessTokenResponse;
import com.core.darkcoders.core.dto.LoginRequest;
import com.core.darkcoders.core.dto.LoginResponse;
import com.core.darkcoders.core.dto.RefreshTokenRequest;
import com.core.darkcoders.core.dto.RegistrationResponse;
import com.core.darkcoders.core.model.User;
import com.core.darkcoders.core.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/register/doctor")
    public ResponseEntity<RegistrationResponse> registerDoctor(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.registerUser(request.getEmail(), request.getPassword(), "ROLE_DOCTOR"));
    }

    @PostMapping("/register/patient")
    public ResponseEntity<RegistrationResponse> registerPatient(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.registerUser(request.getEmail(), request.getPassword(), "ROLE_PATIENT"));
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        User user = authService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
} 