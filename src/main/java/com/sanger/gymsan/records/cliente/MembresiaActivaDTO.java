package com.sanger.gymsan.records.cliente;

import java.time.LocalDateTime;

public record MembresiaActivaDTO(
        Long id,
        String nombre,
        LocalDateTime fechaInscripcion,
        LocalDateTime fechaVencimiento,
        LocalDateTime fechaUltimoPago) {
}
