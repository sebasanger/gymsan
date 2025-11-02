package com.sanger.gymsan.dto.entrenamiento;

import java.util.Set;

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
public class CreateEntrenamientoDto {

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private String categoria;

    @NotNull
    private Set<Long> ejercicios;

}
