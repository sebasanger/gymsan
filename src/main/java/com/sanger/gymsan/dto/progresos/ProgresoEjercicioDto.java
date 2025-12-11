package com.sanger.gymsan.dto.progresos;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgresoEjercicioDto {
    private Long idEjercicio;
    private String nombreEjercicio;
    private Set<ProgresoDto> progresos;
}