package com.sanger.gymsan.services;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.membresiasUsuarios.MembresiaUsuarioPairDTO;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MembresiaUsuarioService extends BaseService<MembresiaUsuario, Long, MembresiaUsuarioRepository> {

    public MembresiaUsuario getMembresiaByDocumento(String documento) {

        MembresiaUsuario membresiaUsuario = this.repository.findByUsuarioDocumentoAndEnabledTrue(documento)
                .orElseThrow(() -> new MembresiaNotEncontradaException());

        return membresiaUsuario;

    }

    public List<MembresiaUsuarioPairDTO> getAllMembresiasUsuariosByUserId(Long userId) {

        List<Object[]> rows = this.repository.findAllMembresiasByUserId(userId);

        return rows.stream()
                .map(row -> {
                    Membresia m = (Membresia) row[0];
                    MembresiaUsuario mu = (MembresiaUsuario) row[1];
                    return new MembresiaUsuarioPairDTO(m, mu);
                })
                .toList();

    }
}
