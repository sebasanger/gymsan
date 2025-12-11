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
public class ProgresoRutinaDto {

    private Long rutinaId;
    private String nombreRutina;
    private Set<ProgresoEntrenamientoDto> progresosEntrenamientos;

}
