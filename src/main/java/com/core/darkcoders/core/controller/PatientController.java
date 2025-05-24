package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.PatientDTO;
import com.core.darkcoders.core.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<PatientDTO> createPatient(@Valid @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.createPatient(patientDTO));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable Long id, @Valid @RequestBody PatientDTO patientDTO) {
        return ResponseEntity.ok(patientService.updatePatient(id, patientDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deletePatient(@PathVariable Long id) {
        patientService.deletePatient(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR') or hasRole('PATIENT')")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Long id) {
        return ResponseEntity.ok(patientService.getPatient(id));
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('DOCTOR')")
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        return ResponseEntity.ok(patientService.getAllPatients());
    }
} 