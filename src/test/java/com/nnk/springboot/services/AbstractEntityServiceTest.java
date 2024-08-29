package com.nnk.springboot.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public abstract class AbstractEntityServiceTest<T> {

//    @InjectMocks
    protected AbstractEntityService<T> service;

    @Mock
    protected JpaRepository<T, Integer> repository;

    protected abstract AbstractEntityService<T> createService(JpaRepository<T, Integer> repository);

    protected abstract T createEntity();

    public void setService(AbstractEntityService<T> service) {
        this.service = service;
    }

    @Test
    void getEntitiesTest() {
        List<T> entities = List.of(createEntity());

        when(repository.findAll()).thenReturn(entities);

        List<T> result = service.getEntities();

        assertThat(result)
                .isNotNull()
                .isEqualTo(entities);
        verify(repository, times(1)).findAll();
    }


    @Test
    void getEntityTest() {
        Optional<T> optional = Optional.of(createEntity());

        when(repository.findById(anyInt())).thenReturn(optional);

        T result = service.getEntity(1);

        assertThat(result)
                .isNotNull()
                .isEqualTo(optional.get());
        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    void getEntityTest_OptionalEmpty() {
        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        T result = service.getEntity(1);

        assertThat(result)
                .isNull();
        verify(repository, times(1)).findById(anyInt());
    }

    @Test
    void saveTest() {
        T entity = createEntity();

        when(repository.save(any())).thenReturn(entity);

        T result = service.save(entity);

        assertThat(result)
                .isNotNull()
                .isEqualTo(entity);
        verify(repository, times(1)).save(any());
    }

    @Test
    void updateTest() {
        T entity = createEntity();

        when(repository.save(any())).thenReturn(entity);

        T result = service.update(entity);

        assertThat(result)
                .isNotNull()
                .isEqualTo(entity);
        verify(repository, times(1)).save(any());
    }

    @Test
    void deleteTest() {
        T entity = createEntity();

        boolean result = service.delete(entity);

        assertTrue(result);
        verify(repository, times(1)).delete(entity);
    }
}