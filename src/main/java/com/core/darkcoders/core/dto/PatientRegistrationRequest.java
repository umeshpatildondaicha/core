package com.core.darkcoders.core.dto;

import com.core.darkcoders.core.model.Gender;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class PatientRegistrationRequest {
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @NotBlank
    @Email
    private String email;
    
    @NotBlank
    private String password;
    
    @NotNull
    private LocalDate dateOfBirth;
    
    @NotNull
    private Gender gender;
    
    @NotBlank
    private String contact;
} 