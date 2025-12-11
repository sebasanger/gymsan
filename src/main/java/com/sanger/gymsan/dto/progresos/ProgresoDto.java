package com.sanger.gymsan.dto.progresos;

import java.time.LocalDate;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sanger.gymsan.models.Serie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProgresoDto {

    private LocalDate fecha;
    private Set<Serie> seriesRealizadas;

    @JsonIgnore
    private String nombreEjercicio;

}