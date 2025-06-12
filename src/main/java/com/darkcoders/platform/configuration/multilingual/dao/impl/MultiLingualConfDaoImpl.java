package com.darkcoders.platform.configuration.multilingual.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.core.darkcoders.core.generic.dao.impl.GenericDaoImpl;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.darkcoders.platform.configuration.multilingual.dao.IMultiLingualConfigurationDao;
import com.darkcoders.platform.configuration.multilingual.model.MultiLingualConfiguration;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
public class MultiLingualConfDaoImpl extends GenericDaoImpl<Integer, MultiLingualConfiguration> implements IMultiLingualConfigurationDao {

    private final Class<MultiLingualConfiguration> entityClass = MultiLingualConfiguration.class;

    public MultiLingualConfDaoImpl() {
        super();
    }

    @Override
    protected Class<MultiLingualConfiguration> getEntityClass() {
        return entityClass;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public MultiLingualConfiguration create(MultiLingualConfiguration entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            log.error("Error creating MultiLingualConfiguration: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create MultiLingualConfiguration", e);
        }
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchMultiLingualByType(String languageValue, String applicationName) {
        var query = entityManager.createQuery(
                "FROM MultiLingualConfiguration WHERE languageType = :languageType AND appName = :appName AND isDeleted = false",
                MultiLingualConfiguration.class);
        query.setParameter("languageType", languageValue);
        query.setParameter("appName", applicationName);
        List<MultiLingualConfiguration> list = query.getResultList();
        return list.stream().collect(Collectors.toMap(MultiLingualConfiguration::getLingualKey, config -> config));
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchLatestMultiLingualByType(String languageType,
            Long modifiedTime, String applicationName) {
        var query = entityManager.createQuery(
                "FROM MultiLingualConfiguration WHERE languageType = :languageType AND appName = :appName " +
                "AND modifiedTime > :modifiedTime AND isDeleted = false",
                MultiLingualConfiguration.class);
        query.setParameter("languageType", languageType);
        query.setParameter("appName", applicationName);
        query.setParameter("modifiedTime", modifiedTime);
        List<MultiLingualConfiguration> list = query.getResultList();
        return list.stream().collect(Collectors.toMap(MultiLingualConfiguration::getLingualKey, config -> config));
    }

    @Override
    public MultiLingualConfiguration fetchLingualByTypeKey(String languageKey, String languageType,
            String applicationName) {
        var query = entityManager.createQuery(
                "FROM MultiLingualConfiguration WHERE lingualKey = :lingualKey AND languageType = :languageType " +
                "AND appName = :appName AND isDeleted = false",
                MultiLingualConfiguration.class);
        query.setParameter("lingualKey", languageKey);
        query.setParameter("languageType", languageType);
        query.setParameter("appName", applicationName);
        return query.getResultList().stream().findFirst().orElse(null);
    }

    @Override
    public List<MultiLingualConfiguration> createMultiLingualProperty(List<MultiLingualConfiguration> languageProperties) {
        for (MultiLingualConfiguration property : languageProperties) {
            entityManager.persist(property);
        }
        return languageProperties;
    }

    @Override
    public Boolean removeMultiConf(List<Integer> idlist) {
        var query = entityManager.createQuery(
                "UPDATE MultiLingualConfiguration SET isDeleted = true WHERE id IN :ids");
        query.setParameter("ids", idlist);
        return query.executeUpdate() > 0;
    }

    @Override
    public List<MultiLingualConfiguration> fetchMultiLingualByTypeKey(String languagekey, String languageType,
            String applicationName) {
        var query = entityManager.createQuery(
                "FROM MultiLingualConfiguration WHERE lingualKey = :lingualKey AND languageType = :languageType " +
                "AND appName = :appName AND isDeleted = false",
                MultiLingualConfiguration.class);
        query.setParameter("lingualKey", languagekey);
        query.setParameter("languageType", languageType);
        query.setParameter("appName", applicationName);
        return query.getResultList();
    }

    @Override
    public int deleteByIdList(List<Integer> ids) {
        var query = entityManager.createQuery(
                "DELETE FROM MultiLingualConfiguration WHERE id IN :ids");
        query.setParameter("ids", ids);
        return query.executeUpdate();
    }

    @Override
    public boolean isDuplicateEntry(String lingualKey, String appName, String languageType) {
        var query = entityManager.createQuery(
                "SELECT COUNT(*) FROM MultiLingualConfiguration WHERE lingualKey = :lingualKey " +
                "AND appName = :appName AND languageType = :languageType AND isDeleted = false",
                Long.class);
        query.setParameter("lingualKey", lingualKey);
        query.setParameter("appName", appName);
        query.setParameter("languageType", languageType);
        return query.getSingleResult() > 0;
    }

    @Override
    public List<MultiLingualConfiguration> getMultiLingualProps(List<CriteriaFilterParameter> filters,
            List<String> projection, Integer ulimit, Integer llimit, String orderByColumnName, String orderType) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }

    @Override
    public Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters) {
        // TODO: Implement this method
        throw new UnsupportedOperationException("Not implemented yet");
    }
} 