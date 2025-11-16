package com.sanger.gymsan.dto.rutina;

import java.util.Set;

import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.Rutina;
import com.sanger.gymsan.models.TipoRutina;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RutinaConFlagDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private TipoRutina tipoRutina;
    private Set<Entrenamiento> entrenamientos;
    private boolean suscripto;

    public static RutinaConFlagDto fromEntity(Rutina rutina, boolean suscripto) {
        return RutinaConFlagDto.builder()
                .id(rutina.getId())
                .nombre(rutina.getNombre())
                .entrenamientos(rutina.getEntrenamientos())
                .descripcion(rutina.getDescripcion())
                .tipoRutina(rutina.getTipoRutina())
                .suscripto(suscripto)
                .build();
    }
}
