package com.sanger.gymsan.dto.progresos;

import java.util.Set;

import com.sanger.gymsan.models.ProgresoEjercicio;
import com.sanger.gymsan.models.Serie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgresoEjercicioDto {

    private Long id;

    private Long ejercicioId;
    private String nombreEjercicio;

    private Integer series;
    private Integer repeticiones;
    private Double peso;

    private Set<Serie> seriesRealizadas;
}
