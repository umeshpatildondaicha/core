package com.core.darkcoders.platform.configuration.baseconfig.dao.impl;

import com.core.darkcoders.platform.configuration.baseconfig.dao.BaseConfigurationDao;
import com.core.darkcoders.platform.configuration.baseconfig.model.BaseConfiguration;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of BaseConfigurationDao.
 */
@Repository
public class BaseConfigurationDaoImpl implements BaseConfigurationDao {

    private static final Logger logger = LogManager.getLogger(BaseConfigurationDaoImpl.class);
    private final EntityManager entityManager;

    public BaseConfigurationDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public BaseConfiguration update(BaseConfiguration configuration) {
        logger.info("Updating base configuration: {}", configuration);
        return entityManager.merge(configuration);
    }

    @Override
    public List<BaseConfiguration> getBaseConfigurations(List<CriteriaFilterParameter> filters,
            List<String> projection, Integer ulimit, Integer llimit, String columnName,
            String orderType, String searchType) {
        logger.info("Getting base configurations with filters: {}", filters);
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BaseConfiguration> query = cb.createQuery(BaseConfiguration.class);
        Root<BaseConfiguration> root = query.from(BaseConfiguration.class);

        List<Predicate> predicates = new ArrayList<>();
        
        if (filters != null) {
            for (CriteriaFilterParameter filter : filters) {
                if (filter.getFieldName() == null || filter.getOperation() == null || filter.getValue() == null) {
                    logger.warn("Skipping invalid filter: {}", filter);
                    continue;
                }
                
                switch (filter.getOperation().toUpperCase()) {
                    case "EQUALS":
                        predicates.add(cb.equal(root.get(filter.getFieldName()), filter.getValue()));
                        break;
                    case "LIKE":
                        predicates.add(cb.like(root.get(filter.getFieldName()), "%" + filter.getValue() + "%"));
                        break;
                    default:
                        logger.warn("Unsupported operation: {}", filter.getOperation());
                        break;
                }
            }
        }

        if (projection != null && !projection.isEmpty()) {
            query.multiselect(projection.stream()
                .map(root::get)
                .toArray(Selection[]::new));
        }

        if (!predicates.isEmpty()) {
            query.where(predicates.toArray(new Predicate[0]));
        }

        if (columnName != null && orderType != null) {
            if ("asc".equalsIgnoreCase(orderType)) {
                query.orderBy(cb.asc(root.get(columnName)));
            } else {
                query.orderBy(cb.desc(root.get(columnName)));
            }
        }

        TypedQuery<BaseConfiguration> typedQuery = entityManager.createQuery(query);
        
        if (llimit != null) {
            typedQuery.setFirstResult(llimit);
        }
        
        if (ulimit != null) {
            typedQuery.setMaxResults(ulimit - (llimit != null ? llimit : 0));
        }

        return typedQuery.getResultList();
    }

    @Override
    public Long getBaseConfigCount(List<CriteriaFilterParameter> filters, String searchType) {
        logger.info("Getting base config count with filters: {}", filters);
        
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<BaseConfiguration> root = query.from(BaseConfiguration.class);

        List<Predicate> predicates = new ArrayList<>();
        
        if (filters != null) {
            for (CriteriaFilterParameter filter : filters) {
                switch (filter.getOperation().toUpperCase()) {
                    case "EQUALS":
                        predicates.add(cb.equal(root.get(filter.getFieldName()), filter.getValue()));
                        break;
                    case "LIKE":
                        predicates.add(cb.like(root.get(filter.getFieldName()), "%" + filter.getValue() + "%"));
                        break;
                }
            }
        }

        query.select(cb.count(root));
        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public BaseConfiguration getBaseConfigByTag(String key, String configTag, String appName) {
        logger.info("Getting base config by tag - key: {}, tag: {}, appName: {}", key, configTag, appName);
        try {
            Query query = entityManager.createNamedQuery("getBaseConfigByTag");
            query.setParameter("configKey", key);
            query.setParameter("configTag", configTag);
            query.setParameter("appName", appName);
            query.setParameter("customerId", 1); // Default to customer ID 1 for now
            return (BaseConfiguration) query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("No base configuration found for key: {}, tag: {}, appName: {}", key, configTag, appName);
            return null;
        }
    }

    @Override
    public List<BaseConfiguration> getBaseConfigByCustomerId(Integer customerId) {
        logger.info("Getting base config by customer ID: {}", customerId);
        Query query = entityManager.createNamedQuery("getBaseConfigByCustomerId");
        query.setParameter("customerId", customerId);
        return query.getResultList();
    }

    @Override
    public BaseConfiguration getBaseConfigByKeyTypeAndAppName(String key, String configType, String appName) {
        logger.info("Getting base config by key: {}, type: {}, appName: {}", key, configType, appName);
        try {
            Query query = entityManager.createNamedQuery("getBaseConfigByKeyTypeAndAppName");
            query.setParameter("configKey", key);
            query.setParameter("configType", configType);
            query.setParameter("appName", appName);
            return (BaseConfiguration) query.getSingleResult();
        } catch (NoResultException e) {
            logger.warn("No base configuration found for key: {}, type: {}, appName: {}", key, configType, appName);
            return null;
        }
    }

    @Override
    public BaseConfiguration isBaseConfigurationExist(String configKey, String configTag, String appName) {
        logger.info("Checking if base configuration exists - key: {}, tag: {}, appName: {}", configKey, configTag, appName);
        return getBaseConfigByTag(configKey, configTag, appName);
    }

    @Override
    @Transactional
    public BaseConfiguration deleteCreateBaseConfiguration(BaseConfiguration baseConfiguration) {
        logger.info("Deleting and creating base configuration: {}", baseConfiguration);
        BaseConfiguration existing = isBaseConfigurationExist(
            baseConfiguration.getConfigKey(),
            baseConfiguration.getConfigTag(),
            baseConfiguration.getApplicationName()
        );
        
        if (existing != null) {
            entityManager.remove(existing);
        }
        
        return entityManager.merge(baseConfiguration);
    }

    @Override
    public BaseConfiguration findByIdAndDisableFilter(Integer id) {
        logger.info("Finding base configuration by ID: {}", id);
        return entityManager.find(BaseConfiguration.class, id);
    }
} 