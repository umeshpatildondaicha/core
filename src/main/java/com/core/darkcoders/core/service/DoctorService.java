package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.DoctorDTO;
import com.core.darkcoders.core.exception.ResourceNotFoundException;
import com.core.darkcoders.core.model.Doctor;
import com.core.darkcoders.core.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    @Transactional
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        try {
            log.info("Creating doctor with email: {}", doctorDTO.getEmail());
            
            // Check if doctor already exists
            if (doctorRepository.existsByEmail(doctorDTO.getEmail())) {
                throw new IllegalArgumentException("Doctor with email " + doctorDTO.getEmail() + " already exists");
            }

            Doctor doctor = new Doctor();
            doctor.setFirstName(doctorDTO.getFirstName());
            doctor.setLastName(doctorDTO.getLastName());
            doctor.setEmail(doctorDTO.getEmail());
            doctor.setRegistrationNumber(doctorDTO.getRegistrationNumber());
            doctor.setSpecialization(doctorDTO.getSpecialization());
            doctor.setHospitalId(doctorDTO.getHospitalId());
            doctor.setDepartmentId(doctorDTO.getDepartmentId());
            doctor.setPersonalNumber(doctorDTO.getPersonalNumber());
            doctor.setGlobalNumber(doctorDTO.getGlobalNumber());
            doctor.setQualifications(doctorDTO.getQualifications());
            doctor.setProfileImageUrl(doctorDTO.getProfileImageUrl());

            Doctor savedDoctor = doctorRepository.save(doctor);
            log.info("Doctor created successfully with ID: {}", savedDoctor.getDoctorId());
            
            return convertToDTO(savedDoctor);
        } catch (Exception e) {
            log.error("Error creating doctor: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create doctor: " + e.getMessage());
        }
    }

    public DoctorDTO getDoctor(Integer id) {
        log.info("Fetching doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));
        return convertToDTO(doctor);
    }

    public List<DoctorDTO> getAllDoctors() {
        log.info("Fetching all doctors");
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DoctorDTO updateDoctor(Integer id, DoctorDTO doctorDTO) {
        log.info("Updating doctor with ID: {}", id);
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Doctor not found with ID: " + id));

        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setRegistrationNumber(doctorDTO.getRegistrationNumber());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setHospitalId(doctorDTO.getHospitalId());
        doctor.setDepartmentId(doctorDTO.getDepartmentId());
        doctor.setPersonalNumber(doctorDTO.getPersonalNumber());
        doctor.setGlobalNumber(doctorDTO.getGlobalNumber());
        doctor.setQualifications(doctorDTO.getQualifications());
        doctor.setProfileImageUrl(doctorDTO.getProfileImageUrl());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        log.info("Doctor updated successfully with ID: {}", updatedDoctor.getDoctorId());
        
        return convertToDTO(updatedDoctor);
    }

    @Transactional
    public void deleteDoctor(Integer id) {
        log.info("Deleting doctor with ID: {}", id);
        if (!doctorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Doctor not found with ID: " + id);
        }
        doctorRepository.deleteById(id);
        log.info("Doctor deleted successfully with ID: {}", id);
    }

    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setDoctorId(doctor.getDoctorId());
        dto.setFirstName(doctor.getFirstName());
        dto.setLastName(doctor.getLastName());
        dto.setEmail(doctor.getEmail());
        dto.setRegistrationNumber(doctor.getRegistrationNumber());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setHospitalId(doctor.getHospitalId());
        dto.setDepartmentId(doctor.getDepartmentId());
        dto.setPersonalNumber(doctor.getPersonalNumber());
        dto.setGlobalNumber(doctor.getGlobalNumber());
        dto.setQualifications(doctor.getQualifications());
        dto.setProfileImageUrl(doctor.getProfileImageUrl());
        dto.setDoctorStatus(doctor.getDoctorStatus());
        return dto;
    }
} 