package com.darkcoders.platform.configuration.multilingual.rest.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.darkcoders.core.generic.utils.Validate;
import com.darkcoders.platform.configuration.multilingual.rest.IMultiLingualConfigRest;
import com.darkcoders.platform.configuration.multilingual.service.IMultiLingualService;
import com.darkcoders.platform.configuration.multilingual.wrapper.MultiLingualFilterWrapper;
import com.darkcoders.platform.configuration.multilingual.wrapper.MultiLingualWrapper;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/base/util/rest/MultiLingualConfiguration")
public class MultiLingualConfigRestImpl implements IMultiLingualConfigRest {

    private static final Logger logger = LogManager.getLogger(MultiLingualConfigRestImpl.class);

    private final IMultiLingualService iMultiLingualService;

    @Autowired
    public MultiLingualConfigRestImpl(IMultiLingualService iMultiLingualService) {
        this.iMultiLingualService = iMultiLingualService;
    }

    @Override
    @PostMapping("/bulkUploadMultiLingualFile")
    public ResponseEntity<Map<String, Integer>> bulkUploadMultiLingualFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("applicationName") String applicationName) {
        try {
            return ResponseEntity.ok(iMultiLingualService.bulkUploadMultiLingualFile(
                    file.getInputStream(), path, applicationName));
        } catch (Exception e) {
            logger.error("Error in bulk upload: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    @PostMapping("/createMultiLingualConf")
    public ResponseEntity<Map<String, String>> createMultiLingualConf(
            @RequestBody MultiLingualWrapper languagePropertiesWrapper) {
        Validate.checkNoneNull(languagePropertiesWrapper);
        return ResponseEntity.ok(iMultiLingualService.createMultiLingualConf(languagePropertiesWrapper));
    }

    @Override
    @PutMapping("/updateMultiLingualConf")
    public ResponseEntity<Map<String, String>> updateMultiLingualConf(
            @RequestBody MultiLingualWrapper languageProperty) {
        Validate.checkNoneNull(languageProperty);
        return ResponseEntity.ok(iMultiLingualService.updateMultiLingualConf(languageProperty));
    }

    @Override
    @GetMapping("/getFileFormat")
    public ResponseEntity<byte[]> getFileFormat(
            @RequestParam("language") String language,
            @RequestParam("applicationName") String applicationName) {
        return ResponseEntity.ok(iMultiLingualService.getFileFormat(language, applicationName));
    }

    @Override
    @GetMapping("/downloadMultiLingualKeys")
    public ResponseEntity<byte[]> downloadMultiLingualKeys(
            @RequestParam("language") String language,
            @RequestParam("applicationName") String applicationName) {
        return ResponseEntity.ok(iMultiLingualService.downloadMultiLingualKeys(language, applicationName));
    }

    @Override
    @PostMapping("/getMultiLingualPropsCount")
    public ResponseEntity<Long> getMultiLingualPropsCount(@RequestBody MultiLingualFilterWrapper wrapper) {
        Validate.checkNoneNull(wrapper);
        return ResponseEntity.ok(iMultiLingualService.getMultiLingualPropsCount(
                wrapper.getFilters(), wrapper.getConvertedLanguage(), wrapper.getConvertLanguageType()).longValue());
    }

    @Override
    @DeleteMapping("/deleteMultiLingualKeyByKeyAndApplName")
    public ResponseEntity<Boolean> deleteMultiLingualKeyByKeyAndApplName(
            @RequestParam("languageKey") String languageKey,
            @RequestParam("applicationName") String applicationName) {
        logger.info("Going to delete Multilingual conf");
        Validate.checkNoneNull(languageKey);
        Validate.checkNoneEmpty(languageKey);
        return ResponseEntity.ok(iMultiLingualService.removeMultiLingualKeyByKeyAndApplName(languageKey, applicationName));
    }

    @Override
    @PostMapping("/getMultiLingualData")
    public ResponseEntity<Map<String, Map<String, String>>> getMultiLingualData(
            @RequestBody MultiLingualFilterWrapper wrapper) {
        Validate.checkNoneNull(wrapper);
        return ResponseEntity.ok(iMultiLingualService.getMultiLingualData(
                wrapper.getFilters(),
                wrapper.getProjectionList(),
                wrapper.getUpLimit(),
                wrapper.getLowerLimit(),
                wrapper.getOrderByColumn(),
                wrapper.getOrderType()));
    }
} 