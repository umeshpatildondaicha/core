package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.PatientDTO;
import com.core.darkcoders.core.model.Patient;
import com.core.darkcoders.core.repository.PatientRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public PatientDTO createPatient(PatientDTO patientDTO) {
        if (patientRepository.existsByEmail(patientDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDTO, patient);
        patient = patientRepository.save(patient);
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    public PatientDTO updatePatient(Long id, PatientDTO patientDTO) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));

        BeanUtils.copyProperties(patientDTO, patient, "patientId", "createdDate", "updatedDate");
        patient = patientRepository.save(patient);
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        patientRepository.delete(patient);
    }

    public PatientDTO getPatient(Long id) {
        Patient patient = patientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Patient not found"));
        PatientDTO patientDTO = new PatientDTO();
        BeanUtils.copyProperties(patient, patientDTO);
        return patientDTO;
    }

    public List<PatientDTO> getAllPatients() {
        return patientRepository.findAll().stream()
                .map(patient -> {
                    PatientDTO dto = new PatientDTO();
                    BeanUtils.copyProperties(patient, dto);
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public Optional<Patient> findByEmail(String email) {
        return patientRepository.findByEmail(email);
    }
} 