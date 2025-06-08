package com.enttribe.platform.configuration.multilingual.service.impl;

import com.enttribe.core.generic.exception.BusinessException;
import com.enttribe.core.generic.service.impl.AbstractService;
import com.enttribe.core.generic.utils.CoreConstants;
import com.enttribe.core.generic.utils.CoreExceptionConstants;
import com.enttribe.core.generic.utils.FieldConstants;
import com.enttribe.core.generic.utils.LogConstants;
import com.enttribe.core.generic.dao.IGenericDao;
import com.enttribe.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.enttribe.platform.configuration.multilingual.dao.IMultiLingualConfigurationDao;
import com.enttribe.platform.configuration.multilingual.model.MultiLingualConfiguration;
import com.enttribe.platform.configuration.multilingual.model.KeyCategory;
import com.enttribe.platform.configuration.multilingual.service.IMultiLingualService;
import com.enttribe.platform.configuration.multilingual.wrapper.MultiLingualWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@Service("MultiLingualServiceImpl")
public class MultiLingualServiceImpl extends AbstractService<Integer, MultiLingualConfiguration>
        implements IMultiLingualService {

    private static final Logger logger = LogManager.getLogger(MultiLingualServiceImpl.class);
    private static final String STRING = "String";
    private static final String EQUALS = "EQUALS";
    private static final String EQUAL = "Equals";
    private static final String LANGUAGE_TYPE = "languageType";
    private static final String MESSAGE_KEY = "Message Key";
    private static final String ENGLISH_VALUE = "English Value";
    private static final String REMARK = "Remark";

    private final IMultiLingualConfigurationDao multiLingualconfDao;
    private final ObjectMapper objectMapper;

    @Autowired
    public MultiLingualServiceImpl(IMultiLingualConfigurationDao multiLingualconfDao, ObjectMapper objectMapper) {
        this.multiLingualconfDao = multiLingualconfDao;
        this.objectMapper = objectMapper;
    }

    @Override
    protected IGenericDao<Integer, MultiLingualConfiguration> getDao() {
        return multiLingualconfDao;
    }

    @Override
    @Transactional
    public Map<String, Integer> bulkUploadMultiLingualFile(InputStream inputStream, String path, String applicationName) {
        // Implementation for bulk upload
        return new HashMap<>();
    }

    @Override
    @Transactional
    public Map<String, String> createMultiLingualConf(MultiLingualWrapper languagePropertiesWrapper) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            List<MultiLingualConfiguration> list = multiLingualconfDao.fetchMultiLingualByTypeKey(
                    languagePropertiesWrapper.getLingualKey(), languagePropertiesWrapper.getLanguageType(),
                    languagePropertiesWrapper.getAppName());
            if (list.isEmpty()) {
                multiLingualconfDao.create(createLP(languagePropertiesWrapper));
            } else {
                List<MultiLingualConfiguration> filteredList = list.stream()
                        .filter(a -> a.getLingualKey().equals(languagePropertiesWrapper.getLingualKey())
                                && a.getValue().equals(languagePropertiesWrapper.getLanguageType())
                                && a.getAppName().equals(languagePropertiesWrapper.getAppName()))
                        .collect(Collectors.toList());

                if (!filteredList.isEmpty()) {
                    throw new BusinessException(
                            languagePropertiesWrapper.getLingualKey() + " already exists");
                } else {
                    multiLingualconfDao.create(createLP(languagePropertiesWrapper));
                }
            }

            resultMap.put(CoreConstants.MESSAGE, languagePropertiesWrapper.getLingualKey()
                    + " created successfully");
        } catch (Exception e) {
            logger.error("Error while creating language property: {} ", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(CoreExceptionConstants.EXCEPTION_SOMETHING_WENT_WRONG);
        }
        return resultMap;
    }

    @Override
    @Transactional
    public Map<String, String> updateMultiLingualConf(MultiLingualWrapper languageProperty) {
        Map<String, String> resultMap = new HashMap<>();
        try {
            MultiLingualConfiguration existingConfig = multiLingualconfDao.fetchLingualByTypeKey(
                    languageProperty.getLingualKey(), languageProperty.getLanguageType(),
                    languageProperty.getAppName());

            if (existingConfig != null) {
                existingConfig.setValue(languageProperty.getValue());
                existingConfig.setDefaultValue(languageProperty.getDefaultValue());
                existingConfig.setCategory(languageProperty.getCategory());
                multiLingualconfDao.update(existingConfig);
                resultMap.put(CoreConstants.MESSAGE, "Updated successfully");
            } else {
                throw new BusinessException(CoreExceptionConstants.EXCEPTION_NO_RECORD_FOUND);
            }
        } catch (Exception e) {
            logger.error("Error while updating language property: {} ", ExceptionUtils.getStackTrace(e));
            throw new BusinessException(CoreExceptionConstants.EXCEPTION_SOMETHING_WENT_WRONG);
        }
        return resultMap;
    }

    @Override
    public byte[] getFileFormat(String language, String applicationName) {
        // Implementation for getting file format
        return new byte[0];
    }

    @Override
    public byte[] downloadMultiLingualKeys(String language, String applicationName) {
        // Implementation for downloading multilingual keys
        return new byte[0];
    }

    @Override
    public Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters, String language, String languageType) {
        return multiLingualconfDao.getMultiLingualPropsCount(filters);
    }

    @Override
    @Transactional
    public Boolean removeMultiLingualKeyByKeyAndApplName(String languageKey, String applicationName) {
        if (applicationName != null && languageKey != null) {
            List<CriteriaFilterParameter> filters = new ArrayList<>();
            CriteriaFilterParameter c1 = new CriteriaFilterParameter(STRING, FieldConstants.APP_NAME, EQUALS, applicationName);
            CriteriaFilterParameter c2 = new CriteriaFilterParameter(STRING, "lingualKey", EQUALS, languageKey);
            filters.add(c1);
            filters.add(c2);
            List<MultiLingualConfiguration> languageproperty = multiLingualconfDao.getMultiLingualProps(filters, null,
                    null, null, null, null);
            List<Integer> languagePropertyPkList = new ArrayList<>();
            for (MultiLingualConfiguration l : languageproperty) {
                if (l.getLingualKey().equals(languageKey)) {
                    languagePropertyPkList.add(l.getId());
                }
            }

            logger.info("Going to delete languageproperties for id's :{} ", languagePropertyPkList);
            return multiLingualconfDao.removeMultiConf(languagePropertyPkList);
        }
        return false;
    }

    @Override
    @Cacheable(value = "getMultiLingualData", 
        key = "{#criteriaFilterParameters, #uLimit, #lLimit, #orderByColumnName, #orderType}")
    public Map<String, Map<String, String>> getMultiLingualData(
            List<CriteriaFilterParameter> criteriaFilterParameters,
            List<String> projection, Integer uLimit, Integer lLimit, 
            String orderByColumnName, String orderType) {

        // Early validation
        if (criteriaFilterParameters == null || criteriaFilterParameters.isEmpty()) {
            return Collections.emptyMap();
        }

        // Optimize language type filter check
        boolean hasLanguageType = criteriaFilterParameters.stream()
                .anyMatch(e -> LANGUAGE_TYPE.equals(e.getLabelType()));

        // Add user language filter if needed
        if (!hasLanguageType) {
            String userLanguage = "en"; // Default to English if not specified
            criteriaFilterParameters.add(new CriteriaFilterParameter(STRING, 
                LANGUAGE_TYPE, EQUAL, userLanguage));
        }

        // Single database call to get all required data
        List<MultiLingualConfiguration> configurations = multiLingualconfDao
                .getMultiLingualProps(criteriaFilterParameters, projection, 
                    uLimit, lLimit, orderByColumnName, orderType);

        // Optimize grouping and mapping
        return configurations.stream()
                .collect(Collectors.groupingBy(
                    MultiLingualConfiguration::getAppName,
                    Collectors.toMap(
                        MultiLingualConfiguration::getLingualKey,
                        MultiLingualConfiguration::getValue,
                        (v1, v2) -> v1, // Keep first value in case of duplicates
                        HashMap::new
                    )
                ));
    }

    private MultiLingualConfiguration createLP(MultiLingualWrapper wrapper) {
        MultiLingualConfiguration lp = new MultiLingualConfiguration();
        lp.setLingualKey(wrapper.getLingualKey());
        lp.setDefaultValue(wrapper.getDefaultValue());
        lp.setValue(wrapper.getValue());
        lp.setLanguageType(wrapper.getLanguageType());
        lp.setCategory(wrapper.getCategory());
        lp.setAppName(wrapper.getAppName());
        lp.setIsDeleted(false);
        return lp;
    }
} 