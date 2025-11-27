package com.sanger.gymsan.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sanger.gymsan.models.MembresiaUsuario;

public interface MembresiaUsuarioRepository extends JpaRepository<MembresiaUsuario, Long> {

    Optional<MembresiaUsuario> findByUsuarioIdAndEnabledTrue(Long usuarioId);

    Optional<MembresiaUsuario> findByUsuarioDocumentoAndEnabledTrue(String documento);

    Optional<MembresiaUsuario> findTopByUsuarioIdAndEnabledIsTrueOrderByIdDesc(Long usuarioId);
}
