package com.core.darkcoders.platform.resourceinfo.component.service;

import com.core.darkcoders.platform.resourceinfo.component.model.Component;
import com.core.darkcoders.platform.resourceinfo.component.model.ComponentType;
import java.util.Date;
import java.util.List;

public interface ComponentService {
    List<Component> getAllComponents();
    Component getComponentById(Integer id);
    List<Component> getComponentsByApplicationName(String applicationName);
    List<Component> getComponentsByType(ComponentType type);
    List<Component> getComponentsByCategory(String category);
    List<Component> getComponentsByParentId(Integer parentId);
    List<Component> getComponentsByUserGroupId(Integer userGroupId);
    List<Component> getComponentsByApplicationId(Integer applicationId);
    List<Component> getEnabledComponents();
    List<Component> getComponentsByCreatorId(Integer creatorId);
    List<Component> getComponentsByModifierId(Integer modifierId);
    List<Component> getComponentsByCreationTime(Date startDate, Date endDate);
    List<Component> getComponentsByModificationTime(Date startDate, Date endDate);
    List<Component> getComponentsByLastUpdatedTime(Date startDate, Date endDate);
    List<Component> getComponentsByEnabledStatus(Boolean enabled);
    List<Component> getComponentsByDeletedStatus(Boolean deleted);
    List<Component> getComponentsByIsApplication(Boolean isApplication);
    List<Component> getComponentsByApplicationNameAndType(String applicationName, ComponentType type);
    List<Component> getComponentsByApplicationNameAndCategory(String applicationName, String category);
    List<Component> getComponentsByApplicationNameAndEnabled(String applicationName, Boolean enabled);
    List<Component> getComponentsByApplicationNameAndDeleted(String applicationName, Boolean deleted);
    List<Component> getComponentsByApplicationNameAndIsApplication(String applicationName, Boolean isApplication);
    List<Component> getComponentsByApplicationNameAndUserGroupId(String applicationName, Integer userGroupId);
    List<Component> getComponentsByApplicationNameAndApplicationId(String applicationName, Integer applicationId);
    List<Component> getComponentsByApplicationNameAndParentId(String applicationName, Integer parentId);
    List<Component> getComponentsByApplicationNameAndCreatorId(String applicationName, Integer creatorId);
    List<Component> getComponentsByApplicationNameAndModifierId(String applicationName, Integer modifierId);
    List<Component> getComponentsByApplicationNameAndCreationTime(String applicationName, Date startDate, Date endDate);
    List<Component> getComponentsByApplicationNameAndModificationTime(String applicationName, Date startDate, Date endDate);
    List<Component> getComponentsByApplicationNameAndLastUpdatedTime(String applicationName, Date startDate, Date endDate);
} 