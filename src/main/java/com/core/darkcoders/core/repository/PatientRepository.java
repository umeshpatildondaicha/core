package com.core.darkcoders.core.repository;

import com.core.darkcoders.core.model.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByContact(String contact);
    boolean existsByEmail(String email);
    boolean existsByContact(String contact);
} 