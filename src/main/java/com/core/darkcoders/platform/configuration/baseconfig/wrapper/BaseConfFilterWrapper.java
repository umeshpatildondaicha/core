package com.core.darkcoders.platform.configuration.baseconfig.wrapper;

import lombok.Data;
import java.util.List;

@Data
public class BaseConfFilterWrapper {
    private List<CriteriaFilterParameter> filters;
    private List<String> projection;
    private Integer ulimit;
    private Integer llimit;
    private String columnName;
    private String orderType;
    private String searchType;
} 