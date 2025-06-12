package com.core.darkcoders.core.generic.service.impl;

import com.core.darkcoders.core.generic.dao.IGenericDao;
import com.core.darkcoders.core.generic.service.IBaseService;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Transactional
public abstract class AbstractService<ID extends Serializable, T> implements IBaseService<ID, T> {

    protected abstract IGenericDao<ID, T> getDao();

    @Override
    public T save(T entity) {
        return getDao().create(entity);
    }

    @Override
    public T update(T entity) {
        return getDao().update(entity);
    }

    @Override
    public void delete(T entity) {
        getDao().delete(entity);
    }

    @Override
    public void deleteById(ID id) {
        getDao().deleteById(id);
    }

    @Override
    public T findById(ID id) {
        return getDao().findById(id);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public List<T> findAll(Map<String, Object> parameters) {
        return getDao().findAll(parameters);
    }

    @Override
    public Long count() {
        return getDao().count();
    }

    @Override
    public Long count(Map<String, Object> parameters) {
        return getDao().count(parameters);
    }
} 