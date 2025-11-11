package com.sanger.gymsan.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
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
}
