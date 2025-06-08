package com.core.darkcoders.platform.patient.model;

import lombok.Data;
import java.time.LocalDate;

@Data
public class Patient {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private LocalDate dateOfBirth;
    private String gender;
    private Boolean isVerified;
    private String otp;
    private LocalDate otpExpiryTime;
    private String createdBy;
    private String updatedBy;
} 