package com.enttribe.platform.configuration.baseconfig.wrapper;

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

    /**
     * Instantiates a new Criteria filter parameter.
     */
    public CriteriaFilterParameter() {
    }

    /**
     * Instantiates a new Criteria filter parameter.
     *
     * @param datatype  the datatype
     * @param labelType the label type
     * @param operation the operation
     * @param value     the value
     */
    public CriteriaFilterParameter(String datatype, String labelType, String operation, Object value) {
        this.datatype = datatype;
        this.labelType = labelType;
        this.operation = operation;
        this.value = value;
    }
} 