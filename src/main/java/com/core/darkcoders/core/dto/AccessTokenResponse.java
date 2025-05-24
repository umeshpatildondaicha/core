package com.core.darkcoders.core.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType;
    private Integer expiresIn;
    private Integer refreshExpiresIn;
    private String scope;
} 