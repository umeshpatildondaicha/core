package com.core.darkcoders.core.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DoctorRegistrationRequest {
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    @NotBlank
    private String registrationNumber;
    
    private String specialization;
} 