package com.core.darkcoders.core.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDao<ID extends Serializable, T> {
    T save(T entity);
    T update(T entity);
    void delete(T entity);
    T findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
} 