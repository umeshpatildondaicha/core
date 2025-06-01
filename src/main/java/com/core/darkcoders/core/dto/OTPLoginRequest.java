package com.core.darkcoders.core.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPLoginRequest {
    @NotBlank(message = "Mobile number is required")
    private String mobileNumber;

    @NotBlank(message = "OTP is required")
    private String otp;
} 