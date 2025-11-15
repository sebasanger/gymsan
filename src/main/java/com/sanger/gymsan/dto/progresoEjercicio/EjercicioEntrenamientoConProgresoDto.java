package com.sanger.gymsan.dto.progresoEjercicio;

import java.util.Set;

import com.sanger.gymsan.models.Ejercicio;
import com.sanger.gymsan.models.EjercicioEntrenamiento;
import com.sanger.gymsan.models.Entrenamiento;
import com.sanger.gymsan.models.ProgresoEjercicio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EjercicioEntrenamientoConProgresoDto {

    private Long id;

    private Entrenamiento entrenamiento;

    private Ejercicio ejercicio;

    private Integer series;
    private Integer repeticiones;
    private Double peso;

    private Set<EjercicioEntrenamiento> ejerciciosAlternativos;

    private ProgresoEjercicio progreso;
}
