package com.sanger.gymsan.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.ProgresoEjercicio;

public interface ProgresoEjercicioRepository extends JpaRepository<ProgresoEjercicio, Long> {
    boolean existsByProgresoRutinaIdAndEjercicioId(Long progresoRutinaId, Long ejercicioId);

    Optional<ProgresoEjercicio> findByProgresoRutinaIdAndEjercicioId(Long progresoRutinaId, Long ejercicioId);

    Set<ProgresoEjercicio> findByProgresoRutinaId(Long progresoRutinaId);
}
