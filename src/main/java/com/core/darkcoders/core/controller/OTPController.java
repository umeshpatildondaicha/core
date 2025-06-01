package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.service.OTPService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth/otp")
@RequiredArgsConstructor
public class OTPController {

    private final OTPService otpService;

    @PostMapping("/initiate")
    public ResponseEntity<String> initiateOTP(@RequestParam String mobileNumber) {
        log.info("Initiating OTP for mobile number: {}", mobileNumber);
        String otp = otpService.generateAndSendOTP(mobileNumber);
        return ResponseEntity.ok("OTP sent successfully to " + mobileNumber + ". OTP: " + otp);
    }

    @PostMapping("/verify")
    public ResponseEntity<String> verifyOTP(@RequestParam String mobileNumber, @RequestParam String otp) {
        log.info("Verifying OTP for mobile number: {}", mobileNumber);
        boolean isValid = otpService.verifyOTP(mobileNumber, otp);
        if (isValid) {
            return ResponseEntity.ok("OTP verified successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid OTP");
        }
    }
} 