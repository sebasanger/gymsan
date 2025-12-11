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
public class ProgresoEntrenamientoDto {
    private Long idEntrenamiento;
    private String nombreEntrenamiento;
    private Set<ProgresoEjercicioDto> progresosEjercicios;

}
