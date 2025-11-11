package com.sanger.gymsan.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.pojections.RutinaView;

public interface RutinaRepository extends JpaRepository<Rutina, Long> {

    @Query("SELECT r FROM Rutina r LEFT JOIN FETCH r.usuarios WHERE r.id = :id")
    Optional<RutinaView> findProjectedById(@Param("id") Long id);

    Set<Rutina> findByUsuarios_Id(Long usuarioId);

}
