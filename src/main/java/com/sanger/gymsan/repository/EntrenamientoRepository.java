package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Entrenamiento;

public interface EntrenamientoRepository extends JpaRepository<Entrenamiento, Long> {

}
