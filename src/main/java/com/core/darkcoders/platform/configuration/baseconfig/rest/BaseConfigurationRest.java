package com.core.darkcoders.platform.configuration.baseconfig.rest;

import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.BaseConfFilterWrapper;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * REST interface for base configuration management.
 */
@RequestMapping(path = "/base/util/rest/BaseConfiguration", produces = MediaType.APPLICATION_JSON_VALUE)
public interface BaseConfigurationRest {

    /**
     * Find base configuration by ID.
     *
     * @param id the ID
     * @return the base configuration
     */
    @GetMapping("/{id}")
    BaseConfiguration findById(@PathVariable Integer id);

    /**
     * Create base configuration.
     *
     * @param systemConfiguration the system configuration
     * @return the created base configuration
     */
    @PostMapping
    BaseConfiguration createBaseConfig(@RequestBody BaseConfiguration systemConfiguration);

    /**
     * Update base configuration.
     *
     * @param systemConfiguration the system configuration
     * @return the updated base configuration
     */
    @PutMapping
    BaseConfiguration updateBaseConfig(@RequestBody BaseConfiguration systemConfiguration);

    /**
     * Get base configuration details.
     *
     * @param wrapper the filter wrapper containing all parameters
     * @return the list of base configurations
     */
    @PostMapping("/getBaseConfigurationDetails")
    List<BaseConfiguration> getBaseConfigurationDetails(@RequestBody BaseConfFilterWrapper wrapper);

    /**
     * Get base config count.
     *
     * @param filters the filters
     * @param searchType the search type
     * @return the count
     */
    @PostMapping("/count")
    Long getBaseConfigCount(
            @RequestBody List<CriteriaFilterParameter> filters,
            @RequestParam(required = false) String searchType);

    /**
     * Get base config by tag.
     *
     * @param configKey the config key
     * @param configTag the config tag
     * @param appName   the app name
     * @param customerName the customer name
     * @return the base configuration
     */
    @GetMapping("/getBaseConfigByTag")
    BaseConfiguration getBaseConfigByTag(
            @RequestParam String configKey,
            @RequestParam String configTag,
            @RequestParam String appName,
            @RequestParam(required = false) String customerName);

    /**
     * Get base config by customer ID.
     *
     * @param customerId the customer ID
     * @return the list of base configurations
     */
    @GetMapping("/customer/{customerId}")
    List<BaseConfiguration> getBaseConfigByCustomerId(@PathVariable Integer customerId);

    /**
     * Create base config from file.
     *
     * @param inputStream the input stream
     * @param fileName    the file name
     * @return the map of results
     * @throws IOException if an I/O error occurs
     */
    @PostMapping("/import")
    Map<String, Long> crateBaseConfigFromFile(
            @RequestParam("file") InputStream inputStream,
            @RequestParam("fileName") String fileName) throws IOException;

    /**
     * Download base configuration.
     *
     * @param applicationName the application name
     * @return the byte array of the configuration
     */
    @GetMapping("/export")
    byte[] downloadBaseConfiguration(@RequestParam String applicationName);

    /**
     * Get base config by key type and app name.
     *
     * @param key        the key
     * @param configType the config type
     * @param appName    the app name
     * @return the base configuration
     */
    @GetMapping("/key-type-app")
    BaseConfiguration getBaseConfigByKeyTypeAndAppName(
            @RequestParam String key,
            @RequestParam String configType,
            @RequestParam String appName);
} 