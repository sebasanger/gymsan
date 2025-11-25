package com.sanger.gymsan.services;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;

public abstract class BaseService<T, ID, R extends JpaRepository<T, ID>> {

    @Autowired
    protected R repository;

    @PersistenceContext
    private EntityManager entityManager;

    public T save(T t) {
        return repository.save(t);
    }

    public Optional<T> findById(ID id) {
        return repository.findById(id);
    }

    public List<T> findAll(boolean includeDeleted) {

        Session session = entityManager.unwrap(Session.class);

        if (!includeDeleted) {
            session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        } else {
            session.disableFilter("deletedFilter");
        }

        return repository.findAll();
    }

    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public T update(T t) {
        return repository.save(t);
    }

    public void delete(T t) {
        if (t instanceof SoftDeletableInterface softDeletable) {
            softDeletable.setDeleted(true);
            repository.save(t);
        } else {
            repository.delete(t);
        }

    }

    public void deleteById(ID id) {
        T entity = this.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        if (entity instanceof SoftDeletableInterface softDeletable) {
            softDeletable.setDeleted(true);
            repository.save(entity);
        } else {
            repository.delete(entity);
        }
    }

    public Optional<T> findByIdIncludingDeleted(ID id) {

        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        return this.repository.findById(id);
    }

    public void recover(ID id) {
        T entity = this.findByIdIncludingDeleted(id)
                .orElseThrow(EntityNotFoundException::new);

        if (entity instanceof SoftDeletableInterface softDeletable) {
            softDeletable.setDeleted(false);
            repository.save(entity);
        } else {
            throw new RuntimeException("La entidad no implementa SoftDeletable");
        }
    }

}
