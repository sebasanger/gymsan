package com.sanger.gymsan.dto.ejercicio;

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
public class CreateEjercicioDto {

    @NotNull
    private String nombre;

    @NotNull
    private String descripcion;

    @NotNull
    private String categoria;

    private Set<String> fotosBase64;
    private Set<String> videosBase64;

}
