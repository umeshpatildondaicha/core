package com.core.darkcoders.platform.configuration.baseconfig.dao;

import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import jakarta.persistence.Tuple;

import java.util.List;

/**
 * Data Access Object interface for base configuration.
 */
public interface BaseConfigurationDao {
    
    /**
     * Update base configuration.
     *
     * @param configuration the configuration
     * @return the updated base configuration
     */
    BaseConfiguration update(BaseConfiguration configuration);

    /**
     * Get base configurations with filters.
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
    List<BaseConfiguration> getBaseConfigurations(List<CriteriaFilterParameter> filters,
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
     * @param key      the key
     * @param configTag the config tag
     * @param appName  the app name
     * @return the base configuration
     */
    BaseConfiguration getBaseConfigByTag(String key, String configTag, String appName);

    /**
     * Get base config by customer ID.
     *
     * @param customerId the customer ID
     * @return the list of base configurations
     */
    List<BaseConfiguration> getBaseConfigByCustomerId(Integer customerId);

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