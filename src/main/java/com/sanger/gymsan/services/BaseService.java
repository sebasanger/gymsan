package com.sanger.gymsan.services;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.exceptions.EntitiesNotFoundException;

public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

    @Autowired
    protected R repository;

    public T save(T t) {
        return repository.save(t);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public List<T> findAll(boolean includingDeletedEntity) {

        List<T> all = repository.findAll();

        if (includingDeletedEntity || all.isEmpty()) {
            return all;
        }

        try {
            Field deletedField = all.get(0).getClass().getDeclaredField("deleted");
            deletedField.setAccessible(true);
            return all.stream()
                    .filter(e -> {
                        try {
                            return !(Boolean) deletedField.get(e);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException("No se puede acceder al campo deleted", ex);
                        }
                    })
                    .toList();
        } catch (NoSuchFieldException e) {
            return all;
        }

    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T update(T t) {
        return repository.save(t);
    }

    public void delete(T t) {
        try {
            Field deletedField = t.getClass().getDeclaredField("deleted");
            deletedField.setAccessible(true);
            deletedField.set(t, true);
            repository.save(t);
        } catch (NoSuchFieldException e) {
            repository.delete(t);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("No se puede acceder al campo deleted", e);
        }
    }

    public void deleteById(ID id) {
        repository.deleteById(id);
    }

}
