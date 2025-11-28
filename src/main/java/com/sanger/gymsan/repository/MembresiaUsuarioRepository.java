package com.sanger.gymsan.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.sanger.gymsan.models.MembresiaUsuario;

public interface MembresiaUsuarioRepository extends JpaRepository<MembresiaUsuario, Long> {

    Optional<MembresiaUsuario> findByUsuarioIdAndEnabledTrue(Long usuarioId);

    Optional<MembresiaUsuario> findByUsuarioDocumentoAndEnabledTrue(String documento);

    Optional<MembresiaUsuario> findTopByUsuarioIdAndEnabledIsTrueOrderByIdDesc(Long usuarioId);

    List<MembresiaUsuario> findByUsuarioId(Long usuarioId);

    @Query("""
            select m, mu
            from Membresia m
            left join m.membresiasUsuarios mu
                   on mu.usuario.id = :userId
            """)
    List<Object[]> findAllMembresiasByUserId(Long userId);
}
