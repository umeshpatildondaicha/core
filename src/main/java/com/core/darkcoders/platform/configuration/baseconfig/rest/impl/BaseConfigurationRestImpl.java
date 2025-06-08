package com.core.darkcoders.platform.configuration.baseconfig.rest.impl;

import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.rest.BaseConfigurationRest;
import com.core.darkcoders.platform.configuration.baseconfig.service.BaseConfigurationService;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.BaseConfFilterWrapper;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Implementation of BaseConfigurationRest.
 */
@RestController
@Primary
@RequestMapping(path = "/base/util/rest/BaseConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
public class BaseConfigurationRestImpl implements BaseConfigurationRest {

    private static final Logger logger = LogManager.getLogger(BaseConfigurationRestImpl.class);
    private final BaseConfigurationService service;

    @Autowired
    public BaseConfigurationRestImpl(BaseConfigurationService service) {
        this.service = service;
    }

    @Override
    public BaseConfiguration findById(Integer id) {
        logger.info("Finding base configuration by ID: {}", id);
        return service.findByIdAndDisableFilter(id);
    }

    @Override
    public BaseConfiguration createBaseConfig(BaseConfiguration systemConfiguration) {
        logger.info("Creating base configuration: {}", systemConfiguration);
        try {
            return service.deleteCreateBaseConfiguration(systemConfiguration);
        } catch (Exception e) {
            logger.error("Error creating base configuration: {}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("Error creating base configuration", e);
        }
    }

    @Override
    public BaseConfiguration updateBaseConfig(BaseConfiguration systemConfiguration) {
        logger.info("Updating base configuration: {}", systemConfiguration);
        try {
            return service.updateBaseConfiguration(systemConfiguration);
        } catch (Exception e) {
            logger.error("Error updating base configuration: {}", ExceptionUtils.getStackTrace(e));
            throw new RuntimeException("Error updating base configuration", e);
        }
    }

    @Override
    @PostMapping("/getBaseConfigurationDetails")
    public List<BaseConfiguration> getBaseConfigurationDetails(@RequestBody BaseConfFilterWrapper wrapper) {
        logger.info("Getting base configuration details with filters: {}", wrapper.getFilters());
        return service.getBaseConfigurationDetails(
            wrapper.getFilters(),
            wrapper.getProjection(),
            wrapper.getUlimit(),
            wrapper.getLlimit(),
            wrapper.getColumnName(),
            wrapper.getOrderType(),
            wrapper.getSearchType()
        );
    }

    @Override
    public Long getBaseConfigCount(List<CriteriaFilterParameter> filters, String searchType) {
        logger.info("Getting base config count with filters: {}", filters);
        return service.getBaseConfigCount(filters, searchType);
    }

    @Override
    @GetMapping("/getBaseConfigByTag")
    public BaseConfiguration getBaseConfigByTag(
            @RequestParam String configKey,
            @RequestParam String configTag,
            @RequestParam String appName,
            @RequestParam(required = false) String customerName) {
        logger.info("Getting base config by tag - key: {}, tag: {}, appName: {}", configKey, configTag, appName);
        return service.getBaseConfigByTag(configKey, configTag, appName, customerName);
    }

    @Override
    public List<BaseConfiguration> getBaseConfigByCustomerId(Integer customerId) {
        logger.info("Getting base config by customer ID: {}", customerId);
        return service.getBaseConfigByCustomerId(customerId);
    }

    @Override
    public Map<String, Long> crateBaseConfigFromFile(InputStream inputStream, String fileName) throws IOException {
        logger.info("Creating base config from file: {}", fileName);
        return service.crateBaseConfigFromFile(inputStream, fileName);
    }

    @Override
    public byte[] downloadBaseConfiguration(String applicationName) {
        logger.info("Downloading base configuration for application: {}", applicationName);
        return service.downloadBaseConfiguration(applicationName);
    }

    @Override
    public BaseConfiguration getBaseConfigByKeyTypeAndAppName(String key, String configType, String appName) {
        logger.info("Getting base config by key: {}, type: {}, appName: {}", key, configType, appName);
        return service.getBaseConfigByKeyTypeAndAppName(key, configType, appName);
    }
} 