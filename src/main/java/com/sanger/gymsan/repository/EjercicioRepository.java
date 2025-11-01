package com.sanger.gymsan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Ejercicio;

public interface EjercicioRepository extends JpaRepository<Ejercicio, Long> {

}
