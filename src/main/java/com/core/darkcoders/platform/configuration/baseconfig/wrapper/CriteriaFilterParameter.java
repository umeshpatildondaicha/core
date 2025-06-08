package com.core.darkcoders.platform.configuration.baseconfig.wrapper;

import lombok.Data;

/**
 * Wrapper class for criteria filter parameters.
 */
@Data
public class CriteriaFilterParameter {
    
    private String dataType;
    private String fieldName;
    private String operation;
    private String value;

    /**
     * Default constructor.
     */
    public CriteriaFilterParameter() {
    }

    /**
     * Constructor with parameters.
     *
     * @param dataType  the data type
     * @param fieldName the field name
     * @param operator  the operator
     * @param value     the value
     */
    public CriteriaFilterParameter(String dataType, String fieldName, String operator, Object value) {
        this.dataType = dataType;
        this.fieldName = fieldName;
        this.operation = operator;
        this.value = value.toString();
    }
} 