package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.MultiLingualRequest;
import com.core.darkcoders.core.service.MultiLingualService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/multilingual")
@RequiredArgsConstructor
public class MultiLingualController {

    @Qualifier("newMultiLingualService")
    private final MultiLingualService multiLingualService;

    @PostMapping("/data")
    public ResponseEntity<Map<String, Map<String, String>>> getMultiLingualData(
            @RequestBody MultiLingualRequest request) {
        log.debug("Received request for multilingual data: {}", request);
        try {
            Map<String, Map<String, String>> result = multiLingualService.getMultiLingualData(
                    request.getFilters(),
                    request.getProjection(),
                    request.getULimit(),
                    request.getLLimit(),
                    request.getOrderByColumnName(),
                    request.getOrderType()
            );
            log.debug("Returning multilingual data: {}", result);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error processing multilingual data request", e);
            throw e;
        }
    }
} 