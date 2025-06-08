package com.core.darkcoders.platform.patient.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRegistrationRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private LocalDate dateOfBirth;
    private String gender;
} 