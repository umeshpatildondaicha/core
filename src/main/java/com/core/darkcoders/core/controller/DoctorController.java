package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.DoctorDTO;
import com.core.darkcoders.core.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @PostMapping
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDTO));
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<DoctorDTO> getDoctor(@PathVariable Integer id) {
        return ResponseEntity.ok(doctorService.getDoctor(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('DOCTOR', 'PATIENT')")
    public ResponseEntity<Iterable<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Integer id, @Valid @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('DOCTOR')")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.noContent().build();
    }
} 