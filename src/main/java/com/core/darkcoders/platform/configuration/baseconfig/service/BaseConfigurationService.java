package com.core.darkcoders.platform.configuration.baseconfig.service;

import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * Service interface for managing base configurations.
 */
public interface BaseConfigurationService {
    
    /**
     * Update base configuration.
     *
     * @param system the system configuration
     * @return the updated base configuration
     */
    BaseConfiguration updateBaseConfiguration(BaseConfiguration system);

    /**
     * Get base configuration details with filters.
     *
     * @param filters    the filters
     * @param projection the projection
     * @param ulimit     the upper limit
     * @param llimit     the lower limit
     * @param columnName the column name
     * @param orderType  the order type
     * @param searchType the search type
     * @return the list of base configurations
     */
    List<BaseConfiguration> getBaseConfigurationDetails(List<CriteriaFilterParameter> filters, 
            List<String> projection, Integer ulimit, Integer llimit, String columnName, 
            String orderType, String searchType);

    /**
     * Get base config count.
     *
     * @param filters the filters
     * @param searchType the search type
     * @return the count
     */
    Long getBaseConfigCount(List<CriteriaFilterParameter> filters, String searchType);

    /**
     * Get base config by tag.
     *
     * @param configKey the config key
     * @param configTag the config tag
     * @param appName   the app name
     * @param customerName the customer name
     * @return the base configuration
     */
    BaseConfiguration getBaseConfigByTag(String configKey, String configTag, String appName, String customerName);
    
    /**
     * Get base config by customer ID.
     *
     * @param customerId the customer ID
     * @return the list of base configurations
     */
    List<BaseConfiguration> getBaseConfigByCustomerId(Integer customerId);

    /**
     * Create base config from file.
     *
     * @param inputStream the input stream
     * @param fileName    the file name
     * @return the map of results
     * @throws IOException if an I/O error occurs
     */
    Map<String, Long> crateBaseConfigFromFile(InputStream inputStream, String fileName) throws IOException;

    /**
     * Download base configuration.
     *
     * @param applicationName the application name
     * @return the byte array of the configuration
     */
    byte[] downloadBaseConfiguration(String applicationName);

    /**
     * Get base config by key type and app name.
     *
     * @param key        the key
     * @param configType the config type
     * @param appName    the app name
     * @return the base configuration
     */
    BaseConfiguration getBaseConfigByKeyTypeAndAppName(String key, String configType, String appName);
    
    /**
     * Check if base configuration exists.
     *
     * @param configKey the config key
     * @param configTag the config tag
     * @param appName   the app name
     * @return the base configuration if exists
     */
    BaseConfiguration isBaseConfigurationExist(String configKey, String configTag, String appName);
    
    /**
     * Delete and create base configuration.
     *
     * @param baseConfiguration the base configuration
     * @return the base configuration
     */
    BaseConfiguration deleteCreateBaseConfiguration(BaseConfiguration baseConfiguration);
    
    /**
     * Find by ID and disable filter.
     *
     * @param id the ID
     * @return the base configuration
     */
    BaseConfiguration findByIdAndDisableFilter(Integer id);
} 