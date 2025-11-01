package com.sanger.gymsan.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.sanger.gymsan.services.BaseService;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;

public abstract class BaseController<E, ID, S extends BaseService<E, ID, ?>> {

    @Autowired
    protected S service;

    @GetMapping("")
    public ResponseEntity<?> getAll() {
        List<E> result = service.findAll();

        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/paginated")
    public ResponseEntity<?> getAllPaginated(
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {

        Page<E> result = service.findAll(pageable);

        if (result.isEmpty()) {
            throw new EntityNotFoundException();
        } else {
            return ResponseEntity.ok().body(result);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable ID id) {
        E result = service.findById(id).orElseThrow(() -> new EntityNotFoundException());
        return ResponseEntity.ok().body(result);
    }

    @PostMapping("")
    public ResponseEntity<E> create(@Valid @RequestBody E newEntity) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(newEntity));
    }

    @PutMapping({"", "/{id}"})
    public ResponseEntity<E> update(@Valid @RequestBody E entity, @PathVariable(required = false) Long id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(entity));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        E entity = service.findById(id).orElseThrow(() -> new EntityNotFoundException());
        service.delete(entity);
        return ResponseEntity.noContent().build();

    }

}
