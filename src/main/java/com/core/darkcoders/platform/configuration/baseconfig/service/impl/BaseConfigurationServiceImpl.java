package com.core.darkcoders.platform.configuration.baseconfig.service.impl;

import com.core.darkcoders.platform.configuration.baseconfig.dao.BaseConfigurationDao;
import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.service.BaseConfigurationService;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.util.*;

/**
 * Implementation of BaseConfigurationService.
 */
@Service
public class BaseConfigurationServiceImpl implements BaseConfigurationService {

    private static final Logger logger = LogManager.getLogger(BaseConfigurationServiceImpl.class);
    private static final Map<String, String> baseConfMap = new HashMap<>();

    @Autowired
    private BaseConfigurationDao dao;

    @Override
    @Transactional
    public BaseConfiguration updateBaseConfiguration(BaseConfiguration systemConfiguration) {
        logger.info("Updating base configuration: {}", systemConfiguration);
        return dao.update(systemConfiguration);
    }

    @Override
    public List<BaseConfiguration> getBaseConfigurationDetails(List<CriteriaFilterParameter> filters,
            List<String> projection, Integer ulimit, Integer llimit, String columnName,
            String orderType, String searchType) {
        logger.info("Getting base configuration details with filters: {}", filters);
        return dao.getBaseConfigurations(filters, projection, ulimit, llimit, columnName, orderType, searchType);
    }

    @Override
    public Long getBaseConfigCount(List<CriteriaFilterParameter> filters, String searchType) {
        logger.info("Getting base config count with filters: {}", filters);
        return dao.getBaseConfigCount(filters, searchType);
    }

    @Override
    @Cacheable(value = "getBaseConfigByTag", key = "#key + '-' + #customerName")
    public BaseConfiguration getBaseConfigByTag(String key, String configTag, String appName, String customerName) {
        logger.info("Getting base config by tag - key: {}, tag: {}, appName: {}", key, configTag, appName);
        BaseConfiguration config = dao.getBaseConfigByTag(key, configTag, appName);
        if (config != null) {
            config.setApplicationName(appName);
        }
        return config;
    }

    @Override
    public List<BaseConfiguration> getBaseConfigByCustomerId(Integer customerId) {
        logger.info("Getting base config by customer ID: {}", customerId);
        return dao.getBaseConfigByCustomerId(customerId);
    }

    @Override
    @Transactional
    public Map<String, Long> crateBaseConfigFromFile(InputStream inputStream, String fileName) throws IOException {
        logger.info("Creating base config from file: {}", fileName);
        // Implementation for file processing
        Map<String, Long> result = new HashMap<>();
        // Add implementation details here
        return result;
    }

    @Override
    public byte[] downloadBaseConfiguration(String applicationName) {
        logger.info("Downloading base configuration for application: {}", applicationName);
        List<CriteriaFilterParameter> filters = new ArrayList<>();
        filters.add(new CriteriaFilterParameter("String", "applicationName", "EQUALS", applicationName, "applicationName"));

        List<String> projection = Arrays.asList("configKey", "configTag", "applicationName", "configValue");

        List<BaseConfiguration> configurations = dao.getBaseConfigurations(filters, projection, null, null, null, null, null);

        try {
            return createCSVBaseConfigurationData(configurations);
        } catch (Exception e) {
            logger.error("Error creating CSV file: {}", e.getMessage());
            throw new RuntimeException("Error creating CSV file", e);
        }
    }

    @Override
    public BaseConfiguration getBaseConfigByKeyTypeAndAppName(String key, String configType, String appName) {
        logger.info("Getting base config by key: {}, type: {}, appName: {}", key, configType, appName);
        return dao.getBaseConfigByKeyTypeAndAppName(key, configType, appName);
    }

    @Override
    public BaseConfiguration isBaseConfigurationExist(String configKey, String configTag, String appName) {
        logger.info("Checking if base configuration exists - key: {}, tag: {}, appName: {}", configKey, configTag, appName);
        return dao.isBaseConfigurationExist(configKey, configTag, appName);
    }

    @Override
    @Transactional
    public BaseConfiguration deleteCreateBaseConfiguration(BaseConfiguration baseConfiguration) {
        logger.info("Deleting and creating base configuration: {}", baseConfiguration);
        return dao.deleteCreateBaseConfiguration(baseConfiguration);
    }

    @Override
    public BaseConfiguration findByIdAndDisableFilter(Integer id) {
        logger.info("Finding base configuration by ID: {}", id);
        return dao.findByIdAndDisableFilter(id);
    }

    private byte[] createCSVBaseConfigurationData(List<BaseConfiguration> configurations) throws IOException {
        File csvOutput = new File("SystemConfigurationData.csv");
        try (FileWriter out = new FileWriter(csvOutput);
             CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT.withHeader("configKey", "configTag", "applicationName", "configValue"))) {
            
            for (BaseConfiguration config : configurations) {
                printer.printRecord(
                    config.getConfigKey(),
                    config.getConfigTag(),
                    config.getApplicationName(),
                    config.getConfigValue()
                );
            }
        }

        try (InputStream targetStream = new FileInputStream(csvOutput)) {
            return org.apache.commons.io.IOUtils.toByteArray(targetStream);
        }
    }
} 