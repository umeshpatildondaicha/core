package com.core.darkcoders.core.dao.impl;

import com.enttribe.core.generic.dao.IGenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Slf4j
@Repository
public abstract class GenericDaoImpl<ID extends Serializable, T> implements IGenericDao<ID, T> {

    private final Class<T> entityClass;

    protected GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @PersistenceContext
    protected EntityManager entityManager;

    @Override
    @Transactional
    public T create(T entity) {
        try {
            entityManager.persist(entity);
            return entity;
        } catch (Exception e) {
            log.error("Error creating entity: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create entity", e);
        }
    }

    @Override
    @Transactional
    public T update(T entity) {
        try {
            return entityManager.merge(entity);
        } catch (Exception e) {
            log.error("Error updating entity: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to update entity", e);
        }
    }

    @Override
    @Transactional
    public void delete(T entity) {
        try {
            entityManager.remove(entity);
        } catch (Exception e) {
            log.error("Error deleting entity: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to delete entity", e);
        }
    }

    @Override
    public T findById(ID id) {
        try {
            return entityManager.find(entityClass, id);
        } catch (Exception e) {
            log.error("Error finding entity by id {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to find entity by id", e);
        }
    }

    @Override
    public List<T> findAll() {
        try {
            return entityManager.createQuery("FROM " + entityClass.getSimpleName(), entityClass)
                    .getResultList();
        } catch (Exception e) {
            log.error("Error finding all entities: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to find all entities", e);
        }
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    protected Class<T> getEntityClass() {
        return entityClass;
    }
} 