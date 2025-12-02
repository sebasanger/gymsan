package com.sanger.gymsan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.membresiasUsuarios.MembresiaUsuarioPairDTO;
import com.sanger.gymsan.exceptions.MembresiaAlreadySucribedException;
import com.sanger.gymsan.exceptions.MembresiaNotEncontradaException;
import com.sanger.gymsan.exceptions.MembresiaNotSucribedException;
import com.sanger.gymsan.models.Membresia;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.MembresiaRepository;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class MembresiaUsuarioService extends BaseService<MembresiaUsuario, Long, MembresiaUsuarioRepository> {

    private final MembresiaRepository membresiaRepository;

    public List<MembresiaUsuario> getAllMembresiasUsuariosActivas() {
        return this.repository.findByEnabledIsTrueOrderByIdDesc();
    }

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

    public MembresiaUsuario unsuscribe(Long membresiaUsuarioId, Usuario user) {

        MembresiaUsuario membresiaUsuario = this.repository.findById(membresiaUsuarioId)
                .orElseThrow(() -> new MembresiaNotSucribedException());

        if (membresiaUsuario.getEnabled().equals(Boolean.TRUE)) {
            membresiaUsuario.setEnabled(false);
        } else {
            throw new MembresiaNotSucribedException();
        }

        return this.repository.save(membresiaUsuario);

    }

    public MembresiaUsuario suscribe(Long membresiaId, Usuario user) {

        Optional<MembresiaUsuario> membresiaUsuarioActual = this.repository.findByUsuarioIdAndEnabledTrue(user.getId());

        // evalua que no sea la membresia actual
        if (membresiaUsuarioActual.isPresent()) {
            Optional.ofNullable(membresiaUsuarioActual.get().getMembresia().getId()).ifPresent(membresiaActualId -> {
                // si es la misma se retorna
                if (membresiaActualId.equals(membresiaId)) {
                    throw new MembresiaAlreadySucribedException();
                    // si no es la misma se saca el enabled a la ultima membresia para guardar la
                    // nueva
                } else {
                    MembresiaUsuario membresiaActual = membresiaUsuarioActual.get();
                    membresiaActual.setEnabled(false);
                    this.repository.save(membresiaActual);
                }

            });

        }

        Membresia membresia = this.membresiaRepository.findById(membresiaId)
                .orElseThrow(() -> new EntityNotFoundException("Membresia  no encontrada"));

        Optional<MembresiaUsuario> currentMembresiaUsuario = this.repository.findByMembresiaIdAndUsuarioId(membresiaId,
                user.getId());

        // si ya tiene una suscripcion a la membresia se activa nuevamente
        if (currentMembresiaUsuario.isPresent()) {
            MembresiaUsuario mu = currentMembresiaUsuario.get();
            mu.setEnabled(true);
            return this.repository.save(mu);

            // si no tiene una suscripcion a la membresia se genera una nueva
        } else {

            MembresiaUsuario nuevaMembresiaUsuario = new MembresiaUsuario();

            nuevaMembresiaUsuario.setDeleted(false);
            nuevaMembresiaUsuario.setEnabled(true);
            nuevaMembresiaUsuario.setMembresia(membresia);
            nuevaMembresiaUsuario.setFechaInscripcion(LocalDateTime.now());
            nuevaMembresiaUsuario.setUsuario(user);

            return this.repository.save(nuevaMembresiaUsuario);
        }

    }
}
