package com.core.darkcoders.core.service.impl;

import com.core.darkcoders.core.service.MultiLingualService;
import com.enttribe.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.enttribe.platform.configuration.multilingual.dao.IMultiLingualConfigurationDao;
import com.enttribe.platform.configuration.multilingual.model.MultiLingualConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MultiLingualServiceImpl implements MultiLingualService {

    private final IMultiLingualConfigurationDao multiLingualConfigurationDao;

    @Override
    public Map<String, Map<String, String>> getMultiLingualData(
            List<CriteriaFilterParameter> filters,
            List<String> projection,
            Integer uLimit,
            Integer lLimit,
            String orderByColumnName,
            String orderType
    ) {
        List<MultiLingualConfiguration> configurations = multiLingualConfigurationDao
                .getMultiLingualProps(filters, projection, uLimit, lLimit, orderByColumnName, orderType);

        return configurations.stream()
                .collect(Collectors.groupingBy(
                    MultiLingualConfiguration::getAppName,
                    Collectors.toMap(
                        MultiLingualConfiguration::getLingualKey,
                        MultiLingualConfiguration::getValue,
                        (v1, v2) -> v1,
                        HashMap::new
                    )
                ));
    }
} 