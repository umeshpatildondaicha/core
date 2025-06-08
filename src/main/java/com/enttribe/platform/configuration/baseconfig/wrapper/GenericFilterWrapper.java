package com.enttribe.platform.configuration.baseconfig.wrapper;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * The type Generic filter wrapper.
 *
 * @author Shiv
 */
@Data
@Schema(name = "GenericFilterWrapper", description = "GenericFilterWrapper class")
public class GenericFilterWrapper {

    @Schema(description = "Table of GenericFilterWrapper")
    private String table;
    
    @Schema(description = "filter of GenericFilterWrapper")
    private List<CriteriaFilterParameter> filterParameter;

    /**
     * Instantiates a new Generic filter wrapper.
     */
    public GenericFilterWrapper() {
        super();
    }

    @Override
    public String toString() {
        return "GenericFilterWrapper [table=" + table + ", filterParameter=" + filterParameter + "]";
    }
} 