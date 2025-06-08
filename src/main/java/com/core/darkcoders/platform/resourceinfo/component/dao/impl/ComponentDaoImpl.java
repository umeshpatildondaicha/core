package com.core.darkcoders.platform.resourceinfo.component.dao.impl;

import com.core.darkcoders.platform.resourceinfo.component.dao.ComponentDao;
import com.core.darkcoders.platform.resourceinfo.component.model.Component;
import com.core.darkcoders.platform.resourceinfo.component.model.ComponentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

@Repository
@Transactional
public class ComponentDaoImpl implements ComponentDao {

    private static final Logger logger = LoggerFactory.getLogger(ComponentDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Component> getAllComponents() {
        logger.info("Getting all components");
        TypedQuery<Component> query = entityManager.createQuery("SELECT c FROM Component c", Component.class);
        return query.getResultList();
    }

    @Override
    public Component getComponentById(Integer id) {
        logger.info("Getting component by id: {}", id);
        return entityManager.find(Component.class, id);
    }

    @Override
    public List<Component> getComponentsByApplicationName(String applicationName) {
        logger.info("Getting components by application name: {}", applicationName);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName", Component.class);
        query.setParameter("applicationName", applicationName);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByType(ComponentType type) {
        logger.info("Getting components by type: {}", type);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.type = :type", Component.class);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByCategory(String category) {
        logger.info("Getting components by category: {}", category);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.category = :category", Component.class);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByParentId(Integer parentId) {
        logger.info("Getting components by parent id: {}", parentId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.parentId = :parentId", Component.class);
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByUserGroupId(Integer userGroupId) {
        logger.info("Getting components by user group id: {}", userGroupId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.userGroupId = :userGroupId", Component.class);
        query.setParameter("userGroupId", userGroupId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationId(Integer applicationId) {
        logger.info("Getting components by application id: {}", applicationId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationId = :applicationId", Component.class);
        query.setParameter("applicationId", applicationId);
        return query.getResultList();
    }

    @Override
    public List<Component> getEnabledComponents() {
        logger.info("Getting enabled components");
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.enabled = true", Component.class);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByCreatorId(Integer creatorId) {
        logger.info("Getting components by creator id: {}", creatorId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.creatorId = :creatorId", Component.class);
        query.setParameter("creatorId", creatorId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByModifierId(Integer modifierId) {
        logger.info("Getting components by modifier id: {}", modifierId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.modifierId = :modifierId", Component.class);
        query.setParameter("modifierId", modifierId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByCreationTime(Date startDate, Date endDate) {
        logger.info("Getting components by creation time between {} and {}", startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.creationTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByModificationTime(Date startDate, Date endDate) {
        logger.info("Getting components by modification time between {} and {}", startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.modificationTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByLastUpdatedTime(Date startDate, Date endDate) {
        logger.info("Getting components by last updated time between {} and {}", startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.lastUpdatedTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByEnabledStatus(Boolean enabled) {
        logger.info("Getting components by enabled status: {}", enabled);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.enabled = :enabled", Component.class);
        query.setParameter("enabled", enabled);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByDeletedStatus(Boolean deleted) {
        logger.info("Getting components by deleted status: {}", deleted);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.deleted = :deleted", Component.class);
        query.setParameter("deleted", deleted);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByIsApplication(Boolean isApplication) {
        logger.info("Getting components by is application: {}", isApplication);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.isApplication = :isApplication", Component.class);
        query.setParameter("isApplication", isApplication);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndType(String applicationName, ComponentType type) {
        logger.info("Getting components by application name: {} and type: {}", applicationName, type);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.type = :type", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("type", type);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCategory(String applicationName, String category) {
        logger.info("Getting components by application name: {} and category: {}", applicationName, category);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.category = :category", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("category", category);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndEnabled(String applicationName, Boolean enabled) {
        logger.info("Getting components by application name: {} and enabled: {}", applicationName, enabled);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.enabled = :enabled", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("enabled", enabled);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndDeleted(String applicationName, Boolean deleted) {
        logger.info("Getting components by application name: {} and deleted: {}", applicationName, deleted);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.deleted = :deleted", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("deleted", deleted);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndIsApplication(String applicationName, Boolean isApplication) {
        logger.info("Getting components by application name: {} and is application: {}", applicationName, isApplication);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.isApplication = :isApplication", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("isApplication", isApplication);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndUserGroupId(String applicationName, Integer userGroupId) {
        logger.info("Getting components by application name: {} and user group id: {}", applicationName, userGroupId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.userGroupId = :userGroupId", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("userGroupId", userGroupId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndApplicationId(String applicationName, Integer applicationId) {
        logger.info("Getting components by application name: {} and application id: {}", applicationName, applicationId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.applicationId = :applicationId", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("applicationId", applicationId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndParentId(String applicationName, Integer parentId) {
        logger.info("Getting components by application name: {} and parent id: {}", applicationName, parentId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.parentId = :parentId", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("parentId", parentId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCreatorId(String applicationName, Integer creatorId) {
        logger.info("Getting components by application name: {} and creator id: {}", applicationName, creatorId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.creatorId = :creatorId", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("creatorId", creatorId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndModifierId(String applicationName, Integer modifierId) {
        logger.info("Getting components by application name: {} and modifier id: {}", applicationName, modifierId);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.modifierId = :modifierId", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("modifierId", modifierId);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndCreationTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and creation time between {} and {}", applicationName, startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.creationTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndModificationTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and modification time between {} and {}", applicationName, startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.modificationTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }

    @Override
    public List<Component> getComponentsByApplicationNameAndLastUpdatedTime(String applicationName, Date startDate, Date endDate) {
        logger.info("Getting components by application name: {} and last updated time between {} and {}", applicationName, startDate, endDate);
        TypedQuery<Component> query = entityManager.createQuery(
            "SELECT c FROM Component c WHERE c.applicationName = :applicationName AND c.lastUpdatedTime BETWEEN :startDate AND :endDate", Component.class);
        query.setParameter("applicationName", applicationName);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        return query.getResultList();
    }
} 