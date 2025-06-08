package com.core.darkcoders.core.service;

import com.enttribe.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;

import java.util.List;
import java.util.Map;

public interface MultiLingualService {
    Map<String, Map<String, String>> getMultiLingualData(
            List<CriteriaFilterParameter> filters,
            List<String> projection,
            Integer uLimit,
            Integer lLimit,
            String orderByColumnName,
            String orderType
    );
} 