package com.core.darkcoders.core.generic.dao.impl;

import com.core.darkcoders.core.generic.dao.IGenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Slf4j
@Transactional
public abstract class GenericDaoImpl<ID extends Serializable, T> implements IGenericDao<ID, T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    @Override
    public T create(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public void delete(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    @Override
    public T findById(ID id) {
        return entityManager.find(getEntityClass(), id);
    }

    @Override
    public List<T> findAll() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);
        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public List<T> findAll(Map<String, Object> parameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(getEntityClass());
        Root<T> root = query.from(getEntityClass());
        query.select(root);

        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach((key, value) -> {
                if (value != null) {
                    query.where(cb.equal(root.get(key), value));
                }
            });
        }

        return entityManager.createQuery(query).getResultList();
    }

    @Override
    public Long count() {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(getEntityClass());
        query.select(cb.count(root));
        return entityManager.createQuery(query).getSingleResult();
    }

    @Override
    public Long count(Map<String, Object> parameters) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> query = cb.createQuery(Long.class);
        Root<T> root = query.from(getEntityClass());
        query.select(cb.count(root));

        if (parameters != null && !parameters.isEmpty()) {
            parameters.forEach((key, value) -> {
                if (value != null) {
                    query.where(cb.equal(root.get(key), value));
                }
            });
        }

        return entityManager.createQuery(query).getSingleResult();
    }
} 