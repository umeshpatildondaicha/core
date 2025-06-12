package com.darkcoders.platform.configuration.baseconfig.wrapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * The type Base conf filter wrapper.
 *
 * @author Shiv
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseConfFilterWrapper implements Serializable {
  
    private static final long serialVersionUID = 3596373324411557292L;
    
    @Schema(description = "Criteria Parameters")
    private List<CriteriaFilterParameter> filters;
    
    /**
     * The Projection.
     */
    @Schema(description = "projectionList ")
    private List<String> projection;
    
    /**
     * The L limit.
     */
    @Schema(description = "lowerLimit  ")
    private Integer lLimit;
    
    /**
     * The U limit.
     */
    @Schema(description = "upLimit")
    private Integer uLimit;
    
    /**
     * The Order by column name.
     */
    @Schema(description = "OrderByColumn")
    private String orderByColumnName;
    
    /**
     * The Order type.
     */
    @Schema(description = "orderType")
    private String orderType;
    
    /**
     * The Type.
     */
    @Schema(description = "orderType")
    private String Type;
    
    /**
     * The Search type.
     */
    @Schema(description = "searchType")
    private String searchType = "local";
} 