package com.sanger.gymsan.dto.rutina;

import java.util.Set;

import com.sanger.gymsan.dto.entrenamiento.CreateEntrenamientoDto;
import com.sanger.gymsan.models.TipoRutina;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateRutinaDto {

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private TipoRutina tipoRutina;

    private Set<Long> usuariosId;

    @NotNull
    private Set<CreateEntrenamientoDto> entrenamientos;

}
