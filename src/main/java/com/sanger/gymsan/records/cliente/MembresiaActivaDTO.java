package com.sanger.gymsan.records.cliente;

import java.time.LocalDateTime;

public record MembresiaActivaDTO(
                Long id,
                Long membresiaUsuarioId,
                String nombre,
                LocalDateTime fechaInscripcion,
                LocalDateTime fechaVencimiento,
                LocalDateTime fechaUltimoPago) {
}
