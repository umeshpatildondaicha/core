package com.darkcoders.platform.configuration.baseconfig.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * The type Criteria filter parameter.
 *
 * @author Shiv
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CriteriaFilterParameter {
    @Schema(description = "labelType of FilterParameter")
    private String labelType;

    @Schema(description = "operation of FilterParameter")
    private String operation;

    @Schema(description = "value of FilterParameter")
    private Object value;

    @Schema(description = "datatype of FilterParameter")
    private String datatype;

    @Schema(description = "fieldName of FilterParameter")
    private String fieldName;

    /**
     * Instantiates a new Criteria filter parameter.
     */
    public CriteriaFilterParameter() {
        super();
    }

    /**
     * Instantiates a new Criteria filter parameter.
     *
     * @param datatype  the datatype
     * @param labelType the label type
     * @param operation the operation
     * @param value     the value
     * @param fieldName the field name
     */
    public CriteriaFilterParameter(String datatype, String labelType, String operation, Object value, String fieldName) {
        this.datatype = datatype;
        this.labelType = labelType;
        this.operation = operation;
        this.value = value;
        this.fieldName = fieldName;
    }

    // Explicit getter methods
    public String getFieldName() {
        return fieldName;
    }

    public String getOperation() {
        return operation;
    }

    public Object getValue() {
        return value;
    }

    public String getLabelType() {
        return labelType;
    }

    public String getDatatype() {
        return datatype;
    }
} 