package com.darkcoders.core.generic.service.impl;

import org.springframework.transaction.annotation.Transactional;

import com.core.darkcoders.core.dao.IGenericDao;
import com.darkcoders.core.generic.service.IBaseService;

import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class AbstractService<ID extends Serializable, T> implements IBaseService<ID, T> {

    protected abstract IGenericDao<ID, T> getDao();

    @Override
    public T create(T entity) {
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
    public T findById(ID id) {
        return getDao().findById(id);
    }

    @Override
    public List<T> findAll() {
        return getDao().findAll();
    }

    @Override
    public void deleteById(ID id) {
        getDao().deleteById(id);
    }
} 