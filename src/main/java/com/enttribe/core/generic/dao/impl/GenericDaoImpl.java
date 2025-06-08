package com.enttribe.core.generic.dao.impl;

import com.enttribe.core.generic.dao.IGenericDao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericDaoImpl<ID extends Serializable, T> implements IGenericDao<ID, T> {

    @PersistenceContext
    protected EntityManager entityManager;

    protected Class<T> entityClass;

    public GenericDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

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
    public T findById(ID id) {
        return entityManager.find(entityClass, id);
    }

    @Override
    public List<T> findAll() {
        return entityManager.createQuery("from " + entityClass.getName(), entityClass).getResultList();
    }

    @Override
    public void deleteById(ID id) {
        T entity = findById(id);
        if (entity != null) {
            delete(entity);
        }
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }
} 