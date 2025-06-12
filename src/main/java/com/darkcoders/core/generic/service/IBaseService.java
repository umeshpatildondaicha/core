package com.darkcoders.core.generic.service;

import java.io.Serializable;
import java.util.List;

public interface IBaseService<ID extends Serializable, T> {
    T create(T entity);
    T update(T entity);
    void delete(T entity);
    T findById(ID id);
    List<T> findAll();
    void deleteById(ID id);
} 