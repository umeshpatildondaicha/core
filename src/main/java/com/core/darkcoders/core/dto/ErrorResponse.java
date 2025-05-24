package com.core.darkcoders.core.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
} 