package com.core.darkcoders.platform.patient.rest;

import com.core.darkcoders.platform.patient.dto.PatientRegistrationRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public interface PatientRest {

    /**
     * Register a new patient.
     *
     * @param request the registration request containing patient details
     * @return response with registration status
     */
    @PostMapping(value = "/register/patient", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> registerPatient(@RequestBody PatientRegistrationRequest request);

    /**
     * Initiate OTP generation for a mobile number.
     *
     * @param mobileNumber the mobile number to send OTP to
     * @return response with OTP sent status
     */
    @PostMapping(value = "/auth/otp/initiate", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> initiateOtp(@RequestParam String mobileNumber);

    /**
     * Verify OTP for login.
     *
     * @param request map containing mobileNumber and otp
     * @return response with verification status
     */
    @PostMapping(value = "/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<?> verifyOtp(@RequestBody Map<String, String> request);
} 