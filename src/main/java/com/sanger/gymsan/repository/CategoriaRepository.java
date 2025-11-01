package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

}
