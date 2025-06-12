package com.darkcoders.platform.configuration.multilingual.service;

import com.core.darkcoders.core.generic.service.IBaseService;
import com.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.darkcoders.platform.configuration.multilingual.model.MultiLingualConfiguration;
import com.darkcoders.platform.configuration.multilingual.wrapper.MultiLingualWrapper;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface IMultiLingualService extends IBaseService<Integer, MultiLingualConfiguration> {

    Map<String, Integer> bulkUploadMultiLingualFile(InputStream inputStream, String path, String applicationName);

    Map<String, String> createMultiLingualConf(MultiLingualWrapper languagePropertiesWrapper);

    Map<String, String> updateMultiLingualConf(MultiLingualWrapper languageProperty);

    byte[] getFileFormat(String language, String applicationName);

    byte[] downloadMultiLingualKeys(String language, String applicationName);

    Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters, String language, String languageType);

    Boolean removeMultiLingualKeyByKeyAndApplName(String languageKey, String applicationName);

    Map<String, Map<String, String>> getMultiLingualData(List<CriteriaFilterParameter> criteriaFilterParameters,
            List<String> projection, Integer uLimit, Integer lLimit, String orderByColumnName, String orderType);
} 