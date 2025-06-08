package com.core.darkcoders.platform.user.model;

import lombok.Data;

@Data
public class UserChoice {
    private Long id;
    private String choiceName;
    private String choiceValue;
    private String choiceType;
    private String description;
    private Boolean isActive;
    private String createdBy;
    private String updatedBy;
} 