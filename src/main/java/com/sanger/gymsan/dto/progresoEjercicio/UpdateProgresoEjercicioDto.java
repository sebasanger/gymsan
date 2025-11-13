package com.sanger.gymsan.dto.progresoEjercicio;

import java.util.List;

import com.sanger.gymsan.models.Serie;

import jakarta.persistence.Id;
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
public class UpdateProgresoEjercicioDto {

    @Id
    private Long id;

    @NotNull
    private Long progresoRutinaId;

    @NotNull
    private Long ejercicioId;

    @NotNull
    private List<Serie> series;

}
