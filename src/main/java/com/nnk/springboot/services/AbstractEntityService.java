package com.nnk.springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class AbstractEntityService<T> implements EntityService<T> {

    protected static final Logger LOGGER = LoggerFactory.getLogger(AbstractEntityService.class);
    protected final String LOG_ID;

    protected final JpaRepository<T, Integer> repository;

    public AbstractEntityService(String LOG_ID, JpaRepository<T, Integer> abstractEntityRepository) {
        this.LOG_ID = LOG_ID;
        this.repository = abstractEntityRepository;
    }

    @Override
    public List<T> getEntities() {
        List<T> entities = repository.findAll();
        LOGGER.info("{} - Retrieved {} {}", LOG_ID, entities.size(), entities.size() <= 1 ? "entity" : "entities");

        return entities;
    }

    @Override
    public T getEntity(Integer id) {
        Optional<T> entity = repository.findById(id);

        if (entity.isPresent()) {
            LOGGER.info("{} - Retrieved entity: {}", LOG_ID, entity.get());
            return entity.get();
        }

        LOGGER.error("{} - No such entity with ID: {}", LOG_ID, id);
        return null;
    }

    @Override
    public T save(T entity) {
        LOGGER.info("{} - Creating entity: {}", LOG_ID, entity);
        return repository.save(entity);
    }

    @Override
    public T update(T entity) {
        LOGGER.info("{} - Updating entity: {}", LOG_ID, entity);
        return repository.save(entity);
    }

    @Override
    public boolean delete(T entity) {
        repository.delete(entity);
        LOGGER.info("{} - Deleted entity: {}", LOG_ID, entity);
        return true;
    }
}
