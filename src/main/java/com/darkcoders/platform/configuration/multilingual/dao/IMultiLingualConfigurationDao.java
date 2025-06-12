package com.darkcoders.platform.configuration.multilingual.dao;

import java.util.List;
import java.util.Map;

import com.core.darkcoders.core.generic.dao.IGenericDao;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.darkcoders.platform.configuration.multilingual.model.MultiLingualConfiguration;

public interface IMultiLingualConfigurationDao extends IGenericDao<Integer, MultiLingualConfiguration> {

    Map<String, MultiLingualConfiguration> fetchMultiLingualByType(String languageValue, String applicationName);

    Map<String, MultiLingualConfiguration> fetchLatestMultiLingualByType(String languageType,
            Long modifiedTime, String applicationName);

    MultiLingualConfiguration fetchLingualByTypeKey(String languageKey, String languageType,
            String applicationName);

    List<MultiLingualConfiguration> createMultiLingualProperty(List<MultiLingualConfiguration> languageProperties);

    List<MultiLingualConfiguration> getMultiLingualProps(List<CriteriaFilterParameter> filters, 
            List<String> projection, Integer ulimit, Integer llimit, String orderByColumnName, String orderType);

    Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters);

    Boolean removeMultiConf(List<Integer> idlist);

    List<MultiLingualConfiguration> fetchMultiLingualByTypeKey(String languagekey, String languageType,
            String applicationName);

    int deleteByIdList(List<Integer> ids);
    
    boolean isDuplicateEntry(String lingualKey, String appName, String languageType);
} 