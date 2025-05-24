package com.core.darkcoders.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private Long patientId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Date of birth is required")
    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
    @NotNull(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Contact number is required")
    private String contact;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;
    @NotBlank(message = "Address is required")
    private String address;
    
    @NotNull(message = "Blood group is required")
    private String bloodGroup;
} 