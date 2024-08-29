package com.nnk.springboot.services;

import java.util.List;

public interface EntityService<T> {

    List<T> getEntities();

    T getEntity(Integer id);

    T save(T entity);

    T update(T entity);

    boolean delete(T entity);
}
