package com.sanger.gymsan.records.cliente;

public record UsuarioConMembresiaActivaDTO(
        Long id,
        String fullName,
        String documento,
        String email,
        Boolean deleted,
        Boolean enabled,
        MembresiaActivaDTO membresiaActiva) {
}
