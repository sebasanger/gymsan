package com.sanger.gymsan.dto.progresoEjercicio;

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
public class EntrenamientoConProgresoDto {

    private Entrenamiento entrenamiento;
    private ProgresoEjercicio progreso;

}
