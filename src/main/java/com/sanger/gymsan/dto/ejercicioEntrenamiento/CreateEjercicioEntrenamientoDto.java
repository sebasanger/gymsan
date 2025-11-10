package com.sanger.gymsan.dto.ejercicioEntrenamiento;

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
public class CreateEjercicioEntrenamientoDto {

    @NotNull
    private Long ejercicioId;

    @NotNull
    private Integer series;

    @NotNull
    private Integer repeticiones;

    @NotNull
    private Double peso;

}
