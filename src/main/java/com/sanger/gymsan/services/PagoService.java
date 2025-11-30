package com.sanger.gymsan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sanger.gymsan.dto.pago.CreatePagoDto;
import com.sanger.gymsan.models.MembresiaUsuario;
import com.sanger.gymsan.models.Pago;
import com.sanger.gymsan.models.Usuario;
import com.sanger.gymsan.repository.MembresiaUsuarioRepository;
import com.sanger.gymsan.repository.PagoRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PagoService extends BaseService<Pago, Long, PagoRepository> {

    private final PagoRepository pagoRepository;
    private final MembresiaUsuarioRepository membresiaUsuarioRepository;

    public List<Pago> getPagosByMembresiaId(Long id) {
        return this.pagoRepository.findByMembresiaUsuario_Id(id);
    }

    public Pago addPago(CreatePagoDto createPagoDto, Usuario user) {

        MembresiaUsuario membresiaUsuario = membresiaUsuarioRepository
                .findById(createPagoDto.getMembresiaUsuarioId())
                .orElseThrow(() -> new RuntimeException("membresiaUsuario no existe"));

        Pago pago = new Pago();

        pago.setFecha(LocalDateTime.now());
        pago.setMembresiaUsuario(membresiaUsuario);
        pago.setTransaction(createPagoDto.getTransaction());
        pago.setMonto(membresiaUsuario.getMembresia().getPrecio());

        pago.setDescripcion("Pago de: " + membresiaUsuario.getMembresia().getNombre());
        pago.setAceptada(Boolean.TRUE);

        // actualizacion de la membresia del usuario
        membresiaUsuario.setFechaUltimoPago(LocalDateTime.now());
        membresiaUsuarioRepository.save(membresiaUsuario);

        return this.pagoRepository.save(pago);
    }
}
