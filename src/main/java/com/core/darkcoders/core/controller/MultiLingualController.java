package com.core.darkcoders.core.controller;

import com.core.darkcoders.core.dto.MultiLingualRequest;
import com.core.darkcoders.core.service.MultiLingualService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/multilingual")
@RequiredArgsConstructor
public class MultiLingualController {

    private final MultiLingualService multiLingualService;

    @PostMapping("/data")
    public ResponseEntity<Map<String, Map<String, String>>> getMultiLingualData(
            @RequestBody MultiLingualRequest request) {
        return ResponseEntity.ok(multiLingualService.getMultiLingualData(
                request.getFilters(),
                request.getProjection(),
                request.getULimit(),
                request.getLLimit(),
                request.getOrderByColumnName(),
                request.getOrderType()
        ));
    }
} 