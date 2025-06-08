package com.core.darkcoders.core.dto;

import com.core.darkcoders.core.model.DoctorStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class DoctorDTO {
    private Integer doctorId;

    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    private String password;

    @NotBlank(message = "Registration number is required")
    private String registrationNumber;

    @NotBlank(message = "Specialization is required")
    private String specialization;

    private Integer hospitalId;
    private Integer departmentId;
    private String personalNumber;
    private String globalNumber;
    private DoctorStatus doctorStatus = DoctorStatus.ACTIVE; // Default value

    private String qualifications;
    private String profileImageUrl;
} 