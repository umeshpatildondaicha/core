package com.core.darkcoders.core.generic.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface IBaseService<ID extends Serializable, T> {
    
    T save(T entity);
    
    T update(T entity);
    
    void delete(T entity);
    
    void deleteById(ID id);
    
    T findById(ID id);
    
    List<T> findAll();
    
    List<T> findAll(Map<String, Object> parameters);
    
    Long count();
    
    Long count(Map<String, Object> parameters);
} 