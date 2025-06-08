package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.DoctorRegistrationRequest;
import com.core.darkcoders.core.dto.PatientRegistrationRequest;
import com.core.darkcoders.core.model.Doctor;
import com.core.darkcoders.core.model.Patient;
import com.core.darkcoders.core.service.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/registration")
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/doctor")
    public ResponseEntity<Doctor> registerDoctor(@Valid @RequestBody DoctorRegistrationRequest request) {
        log.info("Received doctor registration request for email: {}", request.getEmail());
        Doctor doctor = registrationService.registerDoctor(request);
        return ResponseEntity.ok(doctor);
    }

    @PostMapping("/patient")
    public ResponseEntity<Patient> registerPatient(@Valid @RequestBody PatientRegistrationRequest request) {
        log.info("Received patient registration request for email: {}", request.getEmail());
        Patient patient = registrationService.registerPatient(request);
        return ResponseEntity.ok(patient);
    }
} 