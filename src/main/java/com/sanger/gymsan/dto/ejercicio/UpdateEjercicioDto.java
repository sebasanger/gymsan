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
public class UpdateEjercicioDto {

    @NotNull
    private Long id;

    private String nombre;

    private String descripcion;

    private String categoria;

    private Set<String> fotosBase64;
    private Set<String> videosBase64;

}
