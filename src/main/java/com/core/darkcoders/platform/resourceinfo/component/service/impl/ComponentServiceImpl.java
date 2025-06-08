package com.core.darkcoders.platform.resourceinfo.component.service.impl;

import com.core.darkcoders.platform.resourceinfo.component.dao.ComponentDao;
import com.core.darkcoders.platform.resourceinfo.component.model.Component;
import com.core.darkcoders.platform.resourceinfo.component.model.ComponentType;
import com.core.darkcoders.platform.resourceinfo.component.service.ComponentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ComponentServiceImpl implements ComponentService {

    private static final Logger logger = LoggerFactory.getLogger(ComponentServiceImpl.class);

    @Autowired
    private ComponentDao componentDao;

    @Override
    public List<Component> getAllComponents() {
        logger.info("Getting all components");
        return componentDao.getAllComponents();
    }

    @Override
    public Component getComponentById(Integer id) {
        logger.info("Getting component by id: {}", id);
        return componentDao.getComponentById(id);
    }

    @Override
    public List<Component> getComponentsByApplicationName(String applicationName) {
        logger.info("Getting components by application name: {}", applicationName);
        return componentDao.getComponentsByApplicationName(applicationName);
    }

    @Override
    public List<Component> getComponentsByType(ComponentType type) {
        logger.info("Getting components by type: {}", type);
        return componentDao.getComponentsByType(type);
    }

    @Override
    public List<Component> getComponentsByCategory(String category) {
        logger.info("Getting components by category: {}", category);
        return componentDao.getComponentsByCategory(category);
    }

    @Override
    public List<Component> getComponentsByParentId(Integer parentId) {
        logger.info("Getting components by parent id: {}", parentId);
        return componentDao.getComponentsByParentId(parentId);
    }

    @Override
    public List<Component> getComponentsByUserGroupId(Integer userGroupId) {
        logger.info("Getting components by user group id: {}", userGroupId);
        return componentDao.getComponentsByUserGroupId(userGroupId);
    }

    @Override
    public List<Component> getComponentsByApplicationId(Integer applicationId) {
        logger.info("Getting components by application id: {}", applicationId);
        return componentDao.getComponentsByApplicationId(applicationId);
    }

    @Override
    public List<Component> getEnabledComponents() {
        logger.info("Getting enabled components");
        return componentDao.getEnabledComponents();
    }

    @Override
    public List<Component> getComponentsByCreatorId(Integer creatorId) {
        logger.info("Getting components by creator id: {}", creatorId);
        return componentDao.getComponentsByCreatorId(creatorId);
    }

    @Override
    public List<Component> getComponentsByModifierId(Integer modifierId) {
        logger.info("Getting components by modifier id: {}", modifierId);
        return componentDao.getComponentsByModifierId(modifierId);
    }

    @Override
    public List<Component> getComponentsByCreationTime(Date startDate, Date endDate) {
        logger.info("Getting components by creation time between {} and {}", startDate, endDate);
        return componentDao.getComponentsByCreationTime(startDate, endDate);
    }

    @Override
    public List<Component> getComponentsByModificationTime(Date startDate, Date endDate) {
        logger.info("Getting components by modification time between {} and {}", startDate, endDate);
        return componentDao.getComponentsByModificationTime(startDate, endDate);
    }

    @Override
    public List<Component> getComponentsByLastUpdatedTime(Date startDate, Date endDate) {
        logger.info("Getting components by last updated time between {} and {}", startDate, endDate);
        return componentDao.getComponentsByLastUpdatedTime(startDate, endDate);
    }

    @Override
    public List<Component> getComponentsByEnabledStatus(Boolean enabled) {
        logger.info("Getting components by enabled status: {}", enabled);
        return componentDao.getComponentsByEnabledStatus(enabled);
    }

    @Override
    public List<Component> getComponentsByDeletedStatus(Boolean deleted) {
        logger.info("Getting components by deleted status: {}", deleted);
        return componentDao.getComponentsByDeletedStatus(deleted);
    }

    @Override
    public List<Component> getComponentsByIsApplication(Boolean isApplication) {
        logger.info("Getting components by is application: {}", isApplication);
        return componentDao.getComponentsByIsApplication(isApplication);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndType(String applicationName, ComponentType type) {
        logger.info("Getting components by application name: {} and type: {}", applicationName, type);
        return componentDao.getComponentsByApplicationNameAndType(applicationName, type);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCategory(String applicationName, String category) {
        logger.info("Getting components by application name: {} and category: {}", applicationName, category);
        return componentDao.getComponentsByApplicationNameAndCategory(applicationName, category);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndEnabled(String applicationName, Boolean enabled) {
        logger.info("Getting components by application name: {} and enabled: {}", applicationName, enabled);
        return componentDao.getComponentsByApplicationNameAndEnabled(applicationName, enabled);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndDeleted(String applicationName, Boolean deleted) {
        logger.info("Getting components by application name: {} and deleted: {}", applicationName, deleted);
        return componentDao.getComponentsByApplicationNameAndDeleted(applicationName, deleted);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndIsApplication(String applicationName, Boolean isApplication) {
        logger.info("Getting components by application name: {} and is application: {}", applicationName, isApplication);
        return componentDao.getComponentsByApplicationNameAndIsApplication(applicationName, isApplication);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndUserGroupId(String applicationName, Integer userGroupId) {
        logger.info("Getting components by application name: {} and user group id: {}", applicationName, userGroupId);
        return componentDao.getComponentsByApplicationNameAndUserGroupId(applicationName, userGroupId);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndApplicationId(String applicationName, Integer applicationId) {
        logger.info("Getting components by application name: {} and application id: {}", applicationName, applicationId);
        return componentDao.getComponentsByApplicationNameAndApplicationId(applicationName, applicationId);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndParentId(String applicationName, Integer parentId) {
        logger.info("Getting components by application name: {} and parent id: {}", applicationName, parentId);
        return componentDao.getComponentsByApplicationNameAndParentId(applicationName, parentId);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCreatorId(String applicationName, Integer creatorId) {
        logger.info("Getting components by application name: {} and creator id: {}", applicationName, creatorId);
        return componentDao.getComponentsByApplicationNameAndCreatorId(applicationName, creatorId);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndModifierId(String applicationName, Integer modifierId) {
        logger.info("Getting components by application name: {} and modifier id: {}", applicationName, modifierId);
        return componentDao.getComponentsByApplicationNameAndModifierId(applicationName, modifierId);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCreationTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and creation time between {} and {}", applicationName, startDate, endDate);
        return componentDao.getComponentsByApplicationNameAndCreationTime(applicationName, startDate, endDate);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndModificationTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and modification time between {} and {}", applicationName, startDate, endDate);
        return componentDao.getComponentsByApplicationNameAndModificationTime(applicationName, startDate, endDate);
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndLastUpdatedTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and last updated time between {} and {}", applicationName, startDate, endDate);
        return componentDao.getComponentsByApplicationNameAndLastUpdatedTime(applicationName, startDate, endDate);
    }
} 