package com.darkcoders.platform.configuration.multilingual.wrapper;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class MultiLingualWrapper {
    private Integer id;

    @NotBlank
    private String lingualKey;

    private String defaultValue;

    @NotBlank
    private String value;

    @NotBlank
    private String languageType;

    private String category;

    private Boolean isDeleted;

    protected Date modifiedTime;

    protected Date createdTime;

    private String appName;

    public MultiLingualWrapper() {
        super();
    }

    public MultiLingualWrapper(Integer id, String lingualKey, String defaultValue, String value,
            String languageType, String category, Boolean isDeleted, Date modifiedTime,
            Date createdTime, String appName) {
        this.id = id;
        this.lingualKey = lingualKey;
        this.defaultValue = defaultValue;
        this.value = value;
        this.languageType = languageType;
        this.category = category;
        this.isDeleted = isDeleted;
        this.modifiedTime = modifiedTime;
        this.createdTime = createdTime;
        this.appName = appName;
    }
} 