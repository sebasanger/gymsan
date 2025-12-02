package com.sanger.gymsan.records.cliente;

import java.time.LocalDateTime;

public record MembresiaActivaDTO(
        Long id,
        Long membresiaUsuarioId,
        String nombre,
        String descripcion,
        Double precio,
        LocalDateTime fechaInscripcion,
        LocalDateTime fechaVencimiento,
        LocalDateTime fechaUltimoPago) {
}
