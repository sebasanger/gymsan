package com.sanger.gymsan.dto.rutina;

import java.util.Set;

import com.sanger.gymsan.dto.entrenamiento.CreateEntrenamientoDto;

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
public class UpdateRutinaDto {

    @NotNull
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    private Set<Long> usuariosId;

    @NotNull
    private Set<CreateEntrenamientoDto> entrenamientos;

}
