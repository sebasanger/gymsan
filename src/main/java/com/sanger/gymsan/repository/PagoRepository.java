package com.sanger.gymsan.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.Pago;

public interface PagoRepository extends JpaRepository<Pago, Long> {
    List<Pago> findByMembresiasUsuarios_Id(Long membresiaId);
}
