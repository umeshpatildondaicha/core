package com.core.darkcoders.core.dao.impl;

import com.enttribe.core.generic.dao.IGenericDao;
import com.enttribe.core.generic.dao.impl.GenericDaoImpl;
import com.enttribe.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.enttribe.platform.configuration.multilingual.dao.IMultiLingualConfigurationDao;
import com.enttribe.platform.configuration.multilingual.model.MultiLingualConfiguration;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Repository
@Primary
public class MultiLingualConfigurationDaoImpl extends GenericDaoImpl<Integer, MultiLingualConfiguration> implements IMultiLingualConfigurationDao {

    public MultiLingualConfigurationDaoImpl() {
        super(MultiLingualConfiguration.class);
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
    public List<MultiLingualConfiguration> getMultiLingualProps(
            List<CriteriaFilterParameter> filters,
            List<String> projection,
            Integer uLimit,
            Integer lLimit,
            String orderByColumnName,
            String orderType
    ) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            if (projection != null && !projection.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (String field : projection) {
                    predicates.add(cb.isNotNull(root.get(field)));
                }
                query.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            if (filters != null && !filters.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (CriteriaFilterParameter filter : filters) {
                    String operation = filter.getOperation().toUpperCase();
                    switch (operation) {
                        case "EQUALS":
                            predicates.add(cb.equal(root.get(filter.getLabelType()), filter.getValue()));
                            break;
                        case "NOT_EQUALS":
                            predicates.add(cb.notEqual(root.get(filter.getLabelType()), filter.getValue()));
                            break;
                        case "GREATER_THAN":
                            predicates.add(cb.greaterThan(root.get(filter.getLabelType()), (Comparable) filter.getValue()));
                            break;
                        case "LESS_THAN":
                            predicates.add(cb.lessThan(root.get(filter.getLabelType()), (Comparable) filter.getValue()));
                            break;
                        case "LIKE":
                            predicates.add(cb.like(root.get(filter.getLabelType()), "%" + filter.getValue() + "%"));
                            break;
                        case "IN":
                            if (filter.getValue() instanceof List) {
                                predicates.add(root.get(filter.getLabelType()).in((List<?>) filter.getValue()));
                            } else if (filter.getValue() instanceof Object[]) {
                                predicates.add(root.get(filter.getLabelType()).in((Object[]) filter.getValue()));
                            } else {
                                throw new IllegalArgumentException("IN operation requires a List or Array value");
                            }
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported operation: " + operation);
                    }
                }
                query.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            if (orderByColumnName != null && !orderByColumnName.isEmpty()) {
                if ("ASC".equalsIgnoreCase(orderType)) {
                    query.orderBy(cb.asc(root.get(orderByColumnName)));
                } else {
                    query.orderBy(cb.desc(root.get(orderByColumnName)));
                }
            }

            var typedQuery = entityManager.createQuery(query);
            if (uLimit != null && lLimit != null) {
                typedQuery.setFirstResult(lLimit);
                typedQuery.setMaxResults(uLimit - lLimit);
            }

            return typedQuery.getResultList();
        } catch (Exception e) {
            log.error("Error getting multilingual properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get multilingual properties", e);
        }
    }

    @Override
    public boolean isDuplicateEntry(String appName, String lingualKey, String language) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.select(cb.count(root));
            query.where(
                cb.and(
                    cb.equal(root.get("appName"), appName),
                    cb.equal(root.get("lingualKey"), lingualKey),
                    cb.equal(root.get("language"), language)
                )
            );

            Long count = entityManager.createQuery(query).getSingleResult();
            return count > 0;
        } catch (Exception e) {
            log.error("Error checking for duplicate entry: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to check for duplicate entry", e);
        }
    }

    @Override
    @Transactional
    public int deleteByIdList(List<Integer> ids) {
        try {
            if (ids != null && !ids.isEmpty()) {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
                Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

                query.where(root.get("id").in(ids));
                List<MultiLingualConfiguration> entities = entityManager.createQuery(query).getResultList();

                int deletedCount = 0;
                for (MultiLingualConfiguration entity : entities) {
                    entityManager.remove(entity);
                    deletedCount++;
                }
                return deletedCount;
            }
            return 0;
        } catch (Exception e) {
            log.error("Error deleting entities by ID list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete entities", e);
        }
    }

    @Override
    public List<MultiLingualConfiguration> fetchMultiLingualByTypeKey(String appName, String type, String key) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), appName),
                    cb.equal(root.get("type"), type),
                    cb.equal(root.get("lingualKey"), key)
                )
            );

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error fetching multilingual by type and key: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch multilingual by type and key", e);
        }
    }

    @Override
    public MultiLingualConfiguration fetchLingualByTypeKey(String appName, String type, String key) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), appName),
                    cb.equal(root.get("type"), type),
                    cb.equal(root.get("lingualKey"), key)
                )
            );

            List<MultiLingualConfiguration> results = entityManager.createQuery(query).getResultList();
            return results.isEmpty() ? null : results.get(0);
        } catch (Exception e) {
            log.error("Error fetching lingual by type and key: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch lingual by type and key", e);
        }
    }

    @Override
    @Transactional
    public Boolean removeMultiConf(List<Integer> ids) {
        try {
            if (ids != null && !ids.isEmpty()) {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
                Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

                query.where(root.get("id").in(ids));
                List<MultiLingualConfiguration> entities = entityManager.createQuery(query).getResultList();

                for (MultiLingualConfiguration entity : entities) {
                    entityManager.remove(entity);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error removing multilingual configurations: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to remove multilingual configurations", e);
        }
    }

    @Override
    public Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.select(cb.count(root));

            if (filters != null && !filters.isEmpty()) {
                List<Predicate> predicates = new ArrayList<>();
                for (CriteriaFilterParameter filter : filters) {
                    String operation = filter.getOperation();
                    switch (operation) {
                        case "EQUALS":
                            predicates.add(cb.equal(root.get(filter.getLabelType()), filter.getValue()));
                            break;
                        case "NOT_EQUALS":
                            predicates.add(cb.notEqual(root.get(filter.getLabelType()), filter.getValue()));
                            break;
                        case "GREATER_THAN":
                            predicates.add(cb.greaterThan(root.get(filter.getLabelType()), (Comparable) filter.getValue()));
                            break;
                        case "LESS_THAN":
                            predicates.add(cb.lessThan(root.get(filter.getLabelType()), (Comparable) filter.getValue()));
                            break;
                        case "LIKE":
                            predicates.add(cb.like(root.get(filter.getLabelType()), "%" + filter.getValue() + "%"));
                            break;
                        default:
                            throw new IllegalArgumentException("Unsupported operation: " + operation);
                    }
                }
                query.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            return entityManager.createQuery(query).getSingleResult().intValue();
        } catch (Exception e) {
            log.error("Error getting multilingual properties count: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get multilingual properties count", e);
        }
    }

    @Override
    @Transactional
    public List<MultiLingualConfiguration> createMultiLingualProperty(List<MultiLingualConfiguration> configurations) {
        try {
            if (configurations != null && !configurations.isEmpty()) {
                List<MultiLingualConfiguration> savedConfigurations = new ArrayList<>();
                for (MultiLingualConfiguration config : configurations) {
                    entityManager.persist(config);
                    savedConfigurations.add(config);
                }
                return savedConfigurations;
            }
            return new ArrayList<>();
        } catch (Exception e) {
            log.error("Error creating multilingual properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create multilingual properties", e);
        }
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchLatestMultiLingualByType(String appName, Long typeId, String language) {
        java.util.Map<String, MultiLingualConfiguration> resultMap = new java.util.HashMap<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            Predicate appNamePredicate = cb.equal(root.get("appName"), appName);
            Predicate typeIdPredicate = cb.equal(root.get("typeId"), typeId);
            Predicate languagePredicate = cb.equal(root.get("language"), language);

            query.where(cb.and(appNamePredicate, typeIdPredicate, languagePredicate));
            query.orderBy(cb.desc(root.get("id")));

            List<MultiLingualConfiguration> results = entityManager.createQuery(query)
                .setMaxResults(1)
                .getResultList();
            if (!results.isEmpty()) {
                resultMap.put(language, results.get(0));
            }
            return resultMap;
        } catch (Exception e) {
            log.error("Error fetching latest multilingual by type: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch latest multilingual by type", e);
        }
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchMultiLingualByType(String appName, String type) {
        java.util.Map<String, MultiLingualConfiguration> resultMap = new java.util.HashMap<>();
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), appName),
                    cb.equal(root.get("type"), type)
                )
            );

            List<MultiLingualConfiguration> results = entityManager.createQuery(query).getResultList();
            for (MultiLingualConfiguration config : results) {
                resultMap.put(config.getLanguageType(), config);
            }
            return resultMap;
        } catch (Exception e) {
            log.error("Error fetching multilingual by type: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch multilingual by type", e);
        }
    }
} 