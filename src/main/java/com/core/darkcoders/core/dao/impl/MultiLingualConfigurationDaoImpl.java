package com.core.darkcoders.core.dao.impl;

import com.core.darkcoders.core.generic.dao.IGenericDao;
import com.core.darkcoders.core.generic.dao.impl.GenericDaoImpl;
import com.core.darkcoders.platform.configuration.baseconfig.wrapper.CriteriaFilterParameter;
import com.darkcoders.platform.configuration.multilingual.dao.IMultiLingualConfigurationDao;
import com.darkcoders.platform.configuration.multilingual.model.MultiLingualConfiguration;
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

    private final Class<MultiLingualConfiguration> entityClass = MultiLingualConfiguration.class;

    public MultiLingualConfigurationDaoImpl() {
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
    @Transactional(readOnly = true)
    public List<MultiLingualConfiguration> getMultiLingualProps(
            List<CriteriaFilterParameter> filters,
            List<String> projection,
            Integer ulimit,
            Integer llimit,
            String orderByColumnName,
            String orderType) {
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

            return entityManager.createQuery(query)
                    .setFirstResult(llimit != null ? llimit : 0)
                    .setMaxResults(ulimit != null ? ulimit : Integer.MAX_VALUE)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error in getMultiLingualProps: ", e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Integer getMultiLingualPropsCount(List<CriteriaFilterParameter> filters) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.select(cb.count(root));

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
                        default:
                            throw new IllegalArgumentException("Unsupported operation: " + operation);
                    }
                }
                query.where(cb.and(predicates.toArray(new Predicate[0])));
            }

            return entityManager.createQuery(query).getSingleResult().intValue();
        } catch (Exception e) {
            log.error("Error in getMultiLingualPropsCount: ", e);
            throw e;
        }
    }

    @Override
    public boolean isDuplicateEntry(String lingualKey, String appName, String languageType) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<Long> query = cb.createQuery(Long.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.select(cb.count(root));
            query.where(
                cb.and(
                    cb.equal(root.get("appName"), appName),
                    cb.equal(root.get("lingualKey"), lingualKey),
                    cb.equal(root.get("language"), languageType)
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

                for (MultiLingualConfiguration entity : entities) {
                    entityManager.remove(entity);
                }
                return entities.size();
            }
            return 0;
        } catch (Exception e) {
            log.error("Error deleting by id list: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete by id list", e);
        }
    }

    @Override
    public List<MultiLingualConfiguration> fetchMultiLingualByTypeKey(String languagekey, String languageType, String applicationName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), applicationName),
                    cb.equal(root.get("lingualKey"), languagekey),
                    cb.equal(root.get("language"), languageType)
                )
            );

            return entityManager.createQuery(query).getResultList();
        } catch (Exception e) {
            log.error("Error fetching multilingual by type key: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch multilingual by type key", e);
        }
    }

    @Override
    public MultiLingualConfiguration fetchLingualByTypeKey(String languageKey, String languageType, String applicationName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), applicationName),
                    cb.equal(root.get("lingualKey"), languageKey),
                    cb.equal(root.get("language"), languageType)
                )
            );

            return entityManager.createQuery(query).getSingleResult();
        } catch (Exception e) {
            log.error("Error fetching lingual by type key: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch lingual by type key", e);
        }
    }

    @Override
    @Transactional
    public Boolean removeMultiConf(List<Integer> idlist) {
        try {
            if (idlist != null && !idlist.isEmpty()) {
                CriteriaBuilder cb = entityManager.getCriteriaBuilder();
                CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
                Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

                query.where(root.get("id").in(idlist));
                List<MultiLingualConfiguration> entities = entityManager.createQuery(query).getResultList();

                for (MultiLingualConfiguration entity : entities) {
                    entityManager.remove(entity);
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            log.error("Error removing multilingual configuration: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to remove multilingual configuration", e);
        }
    }

    @Override
    @Transactional
    public List<MultiLingualConfiguration> createMultiLingualProperty(List<MultiLingualConfiguration> configurations) {
        try {
            List<MultiLingualConfiguration> savedConfigurations = new ArrayList<>();
            for (MultiLingualConfiguration config : configurations) {
                entityManager.persist(config);
                savedConfigurations.add(config);
            }
            return savedConfigurations;
        } catch (Exception e) {
            log.error("Error creating multilingual properties: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create multilingual properties", e);
        }
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchLatestMultiLingualByType(String languageType, Long modifiedTime, String applicationName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), applicationName),
                    cb.equal(root.get("language"), languageType),
                    cb.greaterThan(root.get("modifiedTime"), modifiedTime)
                )
            );

            List<MultiLingualConfiguration> results = entityManager.createQuery(query).getResultList();
            return results.stream().collect(Collectors.toMap(
                MultiLingualConfiguration::getLingualKey,
                config -> config
            ));
        } catch (Exception e) {
            log.error("Error fetching latest multilingual by type: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch latest multilingual by type", e);
        }
    }

    @Override
    public Map<String, MultiLingualConfiguration> fetchMultiLingualByType(String languageValue, String applicationName) {
        try {
            CriteriaBuilder cb = entityManager.getCriteriaBuilder();
            CriteriaQuery<MultiLingualConfiguration> query = cb.createQuery(MultiLingualConfiguration.class);
            Root<MultiLingualConfiguration> root = query.from(MultiLingualConfiguration.class);

            query.where(
                cb.and(
                    cb.equal(root.get("appName"), applicationName),
                    cb.equal(root.get("language"), languageValue)
                )
            );

            List<MultiLingualConfiguration> results = entityManager.createQuery(query).getResultList();
            return results.stream().collect(Collectors.toMap(
                MultiLingualConfiguration::getLingualKey,
                config -> config
            ));
        } catch (Exception e) {
            log.error("Error fetching multilingual by type: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to fetch multilingual by type", e);
        }
    }
} 