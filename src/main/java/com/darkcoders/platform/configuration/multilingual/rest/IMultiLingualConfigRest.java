package com.darkcoders.platform.configuration.multilingual.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.darkcoders.platform.configuration.multilingual.wrapper.MultiLingualFilterWrapper;
import com.darkcoders.platform.configuration.multilingual.wrapper.MultiLingualWrapper;

import java.util.List;
import java.util.Map;

public interface IMultiLingualConfigRest {

    @PostMapping("/bulkUploadMultiLingualFile")
    ResponseEntity<Map<String, Integer>> bulkUploadMultiLingualFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("path") String path,
            @RequestParam("applicationName") String applicationName);

    @PostMapping("/createMultiLingualConf")
    ResponseEntity<Map<String, String>> createMultiLingualConf(
            @RequestBody MultiLingualWrapper languagePropertiesWrapper);

    @PutMapping("/updateMultiLingualConf")
    ResponseEntity<Map<String, String>> updateMultiLingualConf(
            @RequestBody MultiLingualWrapper languageProperty);

    @GetMapping("/getFileFormat")
    ResponseEntity<byte[]> getFileFormat(
            @RequestParam("language") String language,
            @RequestParam("applicationName") String applicationName);

    @GetMapping("/downloadMultiLingualKeys")
    ResponseEntity<byte[]> downloadMultiLingualKeys(
            @RequestParam("language") String language,
            @RequestParam("applicationName") String applicationName);

    @PostMapping("/getMultiLingualPropsCount")
    ResponseEntity<Long> getMultiLingualPropsCount(@RequestBody MultiLingualFilterWrapper wrapper);

    @DeleteMapping("/deleteMultiLingualKeyByKeyAndApplName")
    ResponseEntity<Boolean> deleteMultiLingualKeyByKeyAndApplName(
            @RequestParam("languageKey") String languageKey,
            @RequestParam("applicationName") String applicationName);

    @PostMapping("/getMultiLingualData")
    ResponseEntity<Map<String, Map<String, String>>> getMultiLingualData(
            @RequestBody MultiLingualFilterWrapper wrapper);
} 