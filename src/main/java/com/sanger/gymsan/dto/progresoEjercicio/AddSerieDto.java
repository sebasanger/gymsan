package com.sanger.gymsan.dto.progresoEjercicio;

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
public class AddSerieDto {

    @NotNull
    private Long progresoEjercicioId;

    @NotNull
    private Integer repeticiones;

    @NotNull
    private Double peso;

}
