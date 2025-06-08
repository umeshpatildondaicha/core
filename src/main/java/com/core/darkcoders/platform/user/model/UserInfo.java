package com.core.darkcoders.platform.user.model;

import lombok.Data;

@Data
public class UserInfo {
    private String name;
    private String email;
    private String customerId;
    private String customerName;
    private String preferredUsername;
    private String locale;
    private String givenName;
    private String familyName;
    private Boolean emailVerified;
} 