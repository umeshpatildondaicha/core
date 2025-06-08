package com.core.darkcoders.platform.resourceinfo.component.rest.impl;

import com.core.darkcoders.platform.resourceinfo.component.dto.ComponentResponseDTO;
import com.core.darkcoders.platform.resourceinfo.component.model.Component;
import com.core.darkcoders.platform.resourceinfo.component.model.ComponentType;
import com.core.darkcoders.platform.resourceinfo.component.service.ComponentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/base/util/rest/Component")
@CrossOrigin(origins = "*")
public class ComponentRestImpl {

    @Autowired
    private ComponentService componentService;

    @GetMapping(value = "/findComponentListForUser", produces = "application/json")
    public ResponseEntity<List<ComponentResponseDTO>> findComponentListForUser(
            @RequestParam(required = false) String appName,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer userGroupId) {
        log.info("Finding components for appName: {}, userId: {}, userGroupId: {}", appName, userId, userGroupId);
        
        List<Component> components;
        if (appName != null && !appName.isEmpty()) {
            components = componentService.getComponentsByApplicationName(appName);
        } else {
            components = componentService.getAllComponents();
        }
        
        List<ComponentResponseDTO> response = components.stream()
                .map(ComponentResponseDTO::fromEntity)
                .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getComponentsByType", produces = "application/json")
    public ResponseEntity<List<ComponentResponseDTO>> getComponentsByType(@RequestParam ComponentType type) {
        log.info("Getting components by type: {}", type);
        List<Component> components = componentService.getComponentsByType(type);
        List<ComponentResponseDTO> response = components.stream()
                .map(ComponentResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getComponentsByApplicationNameAndType", produces = "application/json")
    public ResponseEntity<List<ComponentResponseDTO>> getComponentsByApplicationNameAndType(
            @RequestParam String applicationName,
            @RequestParam ComponentType type) {
        log.info("Getting components by application name: {} and type: {}", applicationName, type);
        List<Component> components = componentService.getComponentsByApplicationNameAndType(applicationName, type);
        List<ComponentResponseDTO> response = components.stream()
                .map(ComponentResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

    @GetMapping(value = "/getComponentById", produces = "application/json")
    public ResponseEntity<ComponentResponseDTO> getComponentById(@RequestParam Integer id) {
        log.info("Getting component by id: {}", id);
        Component component = componentService.getComponentById(id);
        return ResponseEntity.ok(ComponentResponseDTO.fromEntity(component));
    }

    @GetMapping(value = "/getComponentsByApplicationName", produces = "application/json")
    public ResponseEntity<List<ComponentResponseDTO>> getComponentsByApplicationName(@RequestParam String applicationName) {
        log.info("Getting components by application name: {}", applicationName);
        List<Component> components = componentService.getComponentsByApplicationName(applicationName);
        List<ComponentResponseDTO> response = components.stream()
                .map(ComponentResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
} 