package com.core.darkcoders.core.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class OTPService {
    
    private final Map<String, String> otpStore = new ConcurrentHashMap<>();
    private final Random random = new Random();

    public String generateAndSendOTP(String mobileNumber) {
        // Generate a 6-digit OTP
        String otp = String.format("%06d", random.nextInt(1000000));
        
        // Store OTP with mobile number
        otpStore.put(mobileNumber, otp);
        
        // TODO: Integrate with SMS service to send OTP
        log.info("Generated OTP for {}: {}", mobileNumber, otp);
        
        return otp;
    }

    public boolean verifyOTP(String mobileNumber, String otp) {
        String storedOTP = otpStore.get(mobileNumber);
        if (storedOTP != null && storedOTP.equals(otp)) {
            // Remove OTP after successful verification
            otpStore.remove(mobileNumber);
            return true;
        }
        return false;
    }
} 