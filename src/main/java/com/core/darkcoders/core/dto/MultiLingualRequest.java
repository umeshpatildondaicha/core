package com.core.darkcoders.core.dto;

import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import lombok.Data;

import java.util.List;

@Data
public class MultiLingualRequest {
    private List<CriteriaFilterParameter> filters;
    private List<String> projection;
    private Integer uLimit;
    private Integer lLimit;
    private String orderByColumnName;
    private String orderType;
} 