package com.sanger.gymsan.dto.rutina;

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
public class UpdateRutinaDto {

    @NotNull
    private Long id;

    private String nombre;

    private String descripcion;

    private String categoria;

    private Set<Long> ejercicios;

}
