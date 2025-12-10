package com.sanger.gymsan.dto.progresoEjercicio;

import java.util.Set;

import com.sanger.gymsan.models.Categoria;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EntrenamientoConProgresoDto {

    private Long id;

    private String nombre;

    private String descripcion;

    private Boolean deleted;

    private Categoria categoria;

    private Set<EjercicioEntrenamientoConProgresoDto> ejercicios;
}
