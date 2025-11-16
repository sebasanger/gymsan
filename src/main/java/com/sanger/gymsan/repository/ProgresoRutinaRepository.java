package com.sanger.gymsan.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.ProgresoRutina;

public interface ProgresoRutinaRepository extends JpaRepository<ProgresoRutina, Long> {

    Optional<ProgresoRutina> findTopByUsuarioIdOrderByFechaDesc(Long usuarioId);

    Optional<ProgresoRutina> findTopByUsuarioIdOrderByCheckInDesc(Long usuarioId);

    Optional<ProgresoRutina> findTopByUsuarioDocumentoAndCheckOutIsNullOrderByCheckInDesc(String documento);

    Optional<ProgresoRutina> findTopByUsuarioIdAndCheckOutIsNullOrderByCheckInDesc(Long usuarioId);

    Set<ProgresoRutina> findByUsuarioIdAndCheckOutIsNotNullOrderByCheckInDesc(Long usuarioId);

    long countByCheckOutIsNull();

}
