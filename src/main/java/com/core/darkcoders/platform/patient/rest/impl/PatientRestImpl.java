package com.core.darkcoders.platform.patient.rest.impl;

import com.core.darkcoders.platform.patient.dto.PatientRegistrationRequest;
import com.core.darkcoders.platform.patient.rest.PatientRest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@Primary
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class PatientRestImpl implements PatientRest {

    private static final Logger logger = LogManager.getLogger(PatientRestImpl.class);
    private static final Map<String, String> otpStore = new HashMap<>();
    private static final Random random = new Random();

    @Override
    @PostMapping(value = "/register/patient", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerPatient(@RequestBody PatientRegistrationRequest request) {
        logger.info("Registering new patient: {}", request);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Patient registered successfully");
        response.put("patient", request);
        
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping(value = "/auth/otp/initiate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initiateOtp(@RequestParam String mobileNumber) {
        logger.info("Initiating OTP for mobile number: {}", mobileNumber);
        
        // Generate OTP
        String otp = generateOTP();
        otpStore.put(mobileNumber, otp);
        
        // In a real application, you would send this OTP via SMS
        logger.info("OTP generated for {}: {}", mobileNumber, otp);
        
        Map<String, Object> response = new HashMap<>();
        response.put("message", "OTP sent successfully");
        response.put("mobileNumber", mobileNumber);
        response.put("otp", otp);
        
        return ResponseEntity.ok(response);
    }

    @Override
    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request) {
        String mobileNumber = request.get("mobileNumber");
        String otp = request.get("otp");
        
        logger.info("Verifying OTP for mobile number: {}", mobileNumber);
        
        String storedOtp = otpStore.get(mobileNumber);
        Map<String, Object> response = new HashMap<>();
        
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpStore.remove(mobileNumber);
            response.put("message", "OTP verified successfully");
            response.put("verified", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Invalid OTP");
            response.put("verified", false);
            return ResponseEntity.badRequest().body(response);
        }
    }

    private String generateOTP() {
        // Generate a 6-digit OTP
        return String.format("%06d", random.nextInt(1000000));
    }
} 