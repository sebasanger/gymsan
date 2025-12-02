package com.sanger.gymsan.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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

        Optional.ofNullable(membresiaUsuario.getMembresia().getCantidadDias())
                .ifPresent(cantidadDias -> {
                    LocalDateTime fechaVencimiento = membresiaUsuario.getFechaVencimiento();
                    LocalDateTime nuevaFecha = fechaVencimiento.plusDays(cantidadDias);
                    membresiaUsuario.setFechaVencimiento(nuevaFecha);
                    this.membresiaUsuarioRepository.save(membresiaUsuario);
                });

        // actualizacion de la membresia del usuario
        membresiaUsuario.setFechaUltimoPago(LocalDateTime.now());
        membresiaUsuarioRepository.save(membresiaUsuario);

        return this.pagoRepository.save(pago);
    }

    public MembresiaUsuario eliminarPago(Long pagoId, Usuario user) {

        Pago pago = this.repository
                .findById(pagoId).orElseThrow(() -> new RuntimeException("Pago no existe"));

        MembresiaUsuario membresiaUsuario = membresiaUsuarioRepository
                .findById(pago.getMembresiaUsuario().getId())
                .orElseThrow(() -> new RuntimeException("Membresia usuario no existe"));

        Optional.ofNullable(pago.getMembresiaUsuario().getMembresia().getCantidadDias())
                .ifPresent(cantidadDias -> {
                    LocalDateTime fechaVencimiento = membresiaUsuario.getFechaVencimiento();
                    LocalDateTime nuevaFecha = fechaVencimiento.minusDays(cantidadDias);
                    membresiaUsuario.setFechaVencimiento(nuevaFecha);
                    this.membresiaUsuarioRepository.save(membresiaUsuario);
                });

        this.pagoRepository.delete(pago);

        return membresiaUsuario;
    }
}
