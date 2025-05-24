package com.core.darkcoders.core.repository;

import com.core.darkcoders.core.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByRegistrationNumber(String registrationNumber);
    Optional<Doctor> findByGlobalNumber(String globalNumber);
    Optional<Doctor> findByPersonalNumber(String personalNumber);
    boolean existsByEmail(String email);
    boolean existsByRegistrationNumber(String registrationNumber);
    boolean existsByGlobalNumber(String globalNumber);
    boolean existsByPersonalNumber(String personalNumber);
} 