package com.sanger.gymsan.dto.progresoRutina;

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
public class CreateProgresoRutinaDto {

    private Long entrenamientoId;

    private Long rutinaId;

}
