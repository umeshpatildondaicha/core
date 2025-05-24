package com.core.darkcoders.core.service;

import com.core.darkcoders.core.dto.DoctorDTO;
import com.core.darkcoders.core.exception.ResourceNotFoundException;
import com.core.darkcoders.core.model.Doctor;
import com.core.darkcoders.core.model.DoctorStatus;
import com.core.darkcoders.core.repository.DoctorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;
    private DoctorDTO doctorDTO;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setDoctorId(1L);
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setEmail("john.doe@example.com");
        doctor.setRegistrationNumber("DOC123");
        doctor.setSpecialization("Cardiology");
        doctor.setDoctorStatus(DoctorStatus.ACTIVE);

        doctorDTO = new DoctorDTO();
        doctorDTO.setFirstName("John");
        doctorDTO.setLastName("Doe");
        doctorDTO.setEmail("john.doe@example.com");
        doctorDTO.setRegistrationNumber("DOC123");
        doctorDTO.setSpecialization("Cardiology");
    }

    @Test
    void createDoctor_Success() {
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        DoctorDTO result = doctorService.createDoctor(doctorDTO);

        assertNotNull(result);
        assertEquals(doctorDTO.getFirstName(), result.getFirstName());
        assertEquals(doctorDTO.getLastName(), result.getLastName());
        verify(doctorRepository, times(1)).save(any(Doctor.class));
    }

    @Test
    void getDoctor_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        DoctorDTO result = doctorService.getDoctor(1L);

        assertNotNull(result);
        assertEquals(doctor.getFirstName(), result.getFirstName());
        assertEquals(doctor.getLastName(), result.getLastName());
    }

    @Test
    void getDoctor_NotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> doctorService.getDoctor(1L));
    }

    @Test
    void getAllDoctors_Success() {
        List<Doctor> doctors = Arrays.asList(doctor);
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<DoctorDTO> result = doctorService.getAllDoctors();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(doctor.getFirstName(), result.get(0).getFirstName());
    }
} 