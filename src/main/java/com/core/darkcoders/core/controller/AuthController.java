package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.*;
import com.core.darkcoders.core.model.AppUser;
import com.core.darkcoders.core.service.AuthService;
import com.core.darkcoders.core.service.OTPService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final OTPService otpService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody OTPLoginRequest loginRequest) {
        // Verify OTP first
        boolean isValidOTP = otpService.verifyOTP(loginRequest.getMobileNumber(), loginRequest.getOtp());
        if (!isValidOTP) {
            return ResponseEntity.badRequest().build();
        }
        
        // If OTP is valid, proceed with login
        return ResponseEntity.ok(authService.loginWithOTP(loginRequest.getMobileNumber()));
    }

    @PostMapping("/register/otp")
    public ResponseEntity<RegistrationResponse> registerWithOTP(@Valid @RequestBody OTPRegistrationRequest request) {
        // Verify OTP first
        boolean isValidOTP = otpService.verifyOTP(request.getMobileNumber(), request.getOtp());
        if (!isValidOTP) {
            return ResponseEntity.badRequest().build();
        }
        
        // If OTP is valid, proceed with registration
        return ResponseEntity.ok(authService.registerUserWithOTP(request));
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
    public ResponseEntity<AppUser> getUserInfo(@PathVariable String username) {
        AppUser user = authService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }
} 