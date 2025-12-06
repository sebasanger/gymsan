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
public class EntrenamientoProgresoDto {

    private Long id;

    private String nombre;

    private String descripcion;

    private Set<ProgresoEjercicioDto> progresosEjercicios;
}
