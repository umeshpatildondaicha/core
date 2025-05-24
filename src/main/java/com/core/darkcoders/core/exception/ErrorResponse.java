package com.core.darkcoders.core.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private HttpStatus status;
    private String errorCode;
    private String message;
    private LocalDateTime timestamp;
} 